package com.example.ecommerce_web.service.impl;

import com.example.ecommerce_web.constant.BookState;
import com.example.ecommerce_web.exceptions.ResourceNotFoundException;
import com.example.ecommerce_web.mapper.BookMapper;
import com.example.ecommerce_web.mapper.BookMapperImpl;
import com.example.ecommerce_web.model.dto.request.BookRequestDTO;
import com.example.ecommerce_web.model.dto.request.ModifyBookRequestDTO;
import com.example.ecommerce_web.model.dto.respond.BookFeatureRespondDTO;
import com.example.ecommerce_web.model.dto.respond.BookRespondDTO;
import com.example.ecommerce_web.model.dto.respond.MessageRespond;
import com.example.ecommerce_web.model.entities.*;
import com.example.ecommerce_web.repository.*;
import com.example.ecommerce_web.security.service.UserLocal;
import com.example.ecommerce_web.service.AuthorService;
import com.example.ecommerce_web.service.BookService;
import com.example.ecommerce_web.service.ClassifyService;
import com.example.ecommerce_web.validator.ListValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;
    ModelMapper modelMapper;
    AuthorService authorService;
    ClassifyRepository classifyRepository;
    AuthorRepository authorRepository;
    UserRepository userRepository;
    UserLocal userLocal;
    CategoryRepository categoryRepository;
    FeedbackRepository feedbackRepository;
    ClassifyService classifyService;
    BookMapper bookMapper;


    @Autowired
    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper
            , AuthorService authorService, ClassifyRepository classifyRepository
            , AuthorRepository authorRepository, UserRepository userRepository, UserLocal userLocal,
                           CategoryRepository categoryRepository, FeedbackRepository feedbackRepository,
                           ClassifyService classifyService, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.authorService = authorService;
        this.classifyRepository = classifyRepository;
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
        this.userLocal = userLocal;
        this.categoryRepository = categoryRepository;
        this.feedbackRepository = feedbackRepository;
        this.classifyService = classifyService;
        this.bookMapper = bookMapper;
    }

    @Override
    public Books getById(int id) {
        return this.bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book Not Found With ID: " + id)
        );
    }


    @Override
    public Page<BookFeatureRespondDTO> getPageBook(String searchCode, String filter, String mode, int page)
    {
        int[] listFilter;
        Pageable pageable = createPage(page, mode);

        if(filter.equals("0"))
        {
            listFilter = this.categoryRepository.findAll().stream()
                    .mapToInt(Category::getCategoryId).toArray();
        }else
        {
            listFilter = Arrays.stream(filter.split(","))
                    .mapToInt(Integer::parseInt).toArray();
        }

        Page<BookFeatureRespondDTO> listBookFeature = bookRepository.getPageBook(pageable, searchCode, listFilter);

        if (listBookFeature.hasContent()){
            return listBookFeature;
        }

        throw new ResourceNotFoundException("This Page Is Empty !!!");
    }


    @Override
    @Transactional
    public Books add(BookRequestDTO bookRequestDTO) {

        int[] listCategoryId = bookRequestDTO.getListCategory();
        int authorId = bookRequestDTO.getAuthorId();
        String userName = userLocal.getLocalUserName();
        Users users = this.userRepository.findUserByUserName(userName).get();

        Author author = authorRepository.findById(authorId)
                                        .orElseThrow(
                                                () -> new ResourceNotFoundException("Not Found Author Witd ID: " + authorId)
                                        );

        List<Classify> classifyList = Arrays.stream(listCategoryId)
                                            .boxed()
                                            .map(id -> classifyService.createClassify(id))
                                            .collect(Collectors.toList());

        Books books = modelMapper.map(bookRequestDTO, Books.class);

        books.setClassifies(classifyList);
        books.setAuthors(author);
        books.setUsers(users);
        books.setBookState(BookState.AVAILABLE);
        Books savedBook = this.bookRepository.save(books);
        classifyList.stream().forEach(classify -> classifyService.updateClassifyWithBook(classify, savedBook));

        return savedBook;
    }


    @Override
    @Transactional
    public void delete(int bookId){
        Books books = getById(bookId);
        this.bookRepository.delete(books);
    }

    @Override
    public Books update(int bookId, ModifyBookRequestDTO request) {
        Books books = getById(bookId);
        Books mappedBook = bookMapper.toExistedBooks(request, books);
        return this.bookRepository.save(mappedBook);
    }

    @Override
    public List<Books> findTopPopular() {
        return null;
    }

    @Override
    public List<Books> findTopRecommend() {
        List<Books> listBookRecommend = this.bookRepository.findAll();
        ListValidator<Books> listBookValid = ListValidator.ofList(listBookRecommend);
        return listBookValid.ifNotEmpty()
                            .stream()
                            .sorted((b1, b2) -> Double.compare(b1.getRatingPoint(), b2.getRatingPoint()))
                            .skip(10)
                            .collect(Collectors.toList());
    }

    private Pageable createPage(int page, String sortBy){
        Sort sort;
        char field = sortBy.charAt(0);
        char mode = sortBy.charAt(1);
        String fieldSort;

        switch (field){
            case 'p':
                fieldSort = "bookPrice";
                break;
            case 'r':
                fieldSort = "ratingPoint";
                break;
            case 'n' :
                fieldSort = "bookName";
                break;
            case 'i':
                fieldSort = "bookId";
                break;
            default:
                throw new ResourceNotFoundException("NOT FOUND FIELD SORT !!!");
        }

        switch (mode){
            case 'a':
                sort = Sort.by( Sort.Direction.ASC,fieldSort);
                break;
            case 'd':
                sort = Sort.by( Sort.Direction.DESC,fieldSort);
                break;
            default:
                throw new ResourceNotFoundException("NOT FOUND MODE SORT !!!");
        }

        return PageRequest.of(page, 20, sort);
    }

}