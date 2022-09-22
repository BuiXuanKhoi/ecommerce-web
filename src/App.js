import logo from './logo.svg';
import './App.css';
import './Component/Auth/Signup/SignUp'
import SignUp from "./Component/Auth/Signup/SignUp";
import Login from "./Component/Auth/login/Login";
import Home from "./Component/home/Home";
import Shop from "./Component/shop/Shop";
import BookDetail from "./Component/books/BookDetail";
import {BrowserRouter, Switch, Route, useParams} from 'react-router-dom';
import Cart from "./Component/cart/Cart";
import Order from "./Component/order/Order";
import Header from "./Component/Header";
import Footer from './Component/Footer';
function App() {
  return (
    <BrowserRouter>
        <Header/>
        <Switch>
            <Route path={'/login'} component={Login}/>
            <Route path={'/signup'} component={SignUp}/>
            <Route path={'/shop'} component={Shop}/>
            <Route path={'/books'} >
                <Route path={':bookId'} component={BookDetail}/>
            </Route>
            <Route path={'/cart'} component={Cart}/>
            <Route path={'/order'} component={Order}/>
            <Route path={'/'} component={Home}/>
        </Switch>
        <Footer/>
    </BrowserRouter>
  );
}

export default App;
