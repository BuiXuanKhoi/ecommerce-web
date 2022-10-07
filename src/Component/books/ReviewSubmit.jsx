import React, {useEffect, useRef, useState} from "react";
import {Row, Col, Menu, Dropdown, Button, Input, Select, Rate, Form,Modal} from "antd";
import './FeedBackTable.css'
import {CaretDownOutlined} from "@ant-design/icons";
import {Option} from "antd/es/mentions";
import TextArea from "antd/es/input/TextArea";
import axios from "axios";

export default function ReviewSubmit ({bookID,config}) {

    const [ratingPoint, setRatingPoint] = useState(3);
    const desc = ['terrible', 'bad', 'normal', 'good', 'wonderful'];
    const [form] = Form.useForm();
    const state = useRef(false);
    const [reviewSubmit, setReviewSubmitting] = useState({
        title: '',
        comment:'',
        ratingPoint: 0.0,
    })


    const handleSendReview = () => {
        axios.post("https://ecommerce-web0903.herokuapp.com/api/books/" + bookID + "/feedbacks", reviewSubmit, config)
            .then((response) => {
                handleSuccess();
            }).catch((error) => {
            if (error.response.data.statusCode === 400) {
                handleError();
            }
            if (error.response.data.statusCode === 404){
                handleAuthenticationError();
            }
        })
    }

    const handleSubmitReview = (values) => {

        setReviewSubmitting({
            title : values.title,
            comment : values.description,
            ratingPoint : ratingPoint,
        })
    }
    const handleAuthenticationError = () =>{
        Modal.error({
            title:"Error",
            content: " You must login first before submitting the feedback"
        })

    }
    useEffect(()=>{
        if(state.current){
            handleSendReview();
        }else{
            state.current = true;
        }
    },[reviewSubmit])

    const handleSuccess = () =>{
        Modal.success({
            content: 'Succeeded in submitting your review',
        });

        window.location.reload();
    }
    const handleError = () => {
        Modal.error({
            title: 'Error',
            content: 'Can not submit two feedbacks for one book',
        });
    };
    return (
        <Row className={"customerReviewPost"}>
            <Col span={24}>
                <Form title={"feedback"}
                      form = {form}
                      onFinish={
                          handleSubmitReview
                      }
                >
                    <Row >
                        <p className="positionForChar" style={{marginLeft:"2%",fontSize:"2vw",fontWeight:"bolder"}}> Write a Review</p>
                    </Row>
                    {/*--------------------------------------------------------------------------------*/}
                    <Row>
                        <Col span={24} style={{borderStyle:"ridge",borderColor:"#F6F6F6"}}>

                        </Col>
                    </Row>

                    {/*--------------------------------------------------------------------------------*/}
                    <Row style={{marginTop:"5%"}}>
                        <p className="positionForChar" style={{marginLeft:"4%",fontSize:"1.2vw"}}>Add a title</p>
                    </Row>
                    {/*--------------------------------------------------------------------------------*/}
                    <Form.Item name= "title">
                        <Row style={{paddingBlock:"0.25em"}}>
                            <Col offset={1} span={22}>

                                <Input  style={{height:"3vw"}}></Input>
                            </Col>
                        </Row>
                    </Form.Item>
                    {/*--------------------------------------------------------------------------------*/}
                    <Row style={{marginTop:"10%"}}>
                        <p className="positionForChar" style={{marginLeft:"4%",fontSize:"1.2vw"}}>
                            Details please! Your review helps other shoppers
                        </p>
                    </Row>
                    {/*--------------------------------------------------------------------------------*/}
                    <Form.Item name="description">
                        <Row style={{paddingBlock:"0.25em"}}>
                            <Col offset={1} span={22}>
                                <TextArea style={{height:"7vw"}}></TextArea>
                            </Col>
                        </Row>
                    </Form.Item>
                    {/*--------------------------------------------------------------------------------*/}
                    <Row style={{marginTop:"10%"}}>
                        <p  className="positionForChar" style={{marginLeft:"4%",fontSize:"1.2vw"}}>
                            Select a rating star
                        </p>
                    </Row>
                    {/*--------------------------------------------------------------------------------*/}

                        <Row style={{paddingBlock:"0.25em"}}>
                            <Col span={24}>
                                <span style={{marginLeft:"5%"}}>
                                    <Rate tooltips={desc} onChange={setRatingPoint} value={ratingPoint}/>
                                    {ratingPoint ? <span className="ant-rate-text">{desc[ratingPoint - 1]}</span>  : ''}
                                </span>
                            </Col>
                        </Row>
                    {/*--------------------------------------------------------------------------------*/}
                    <Row>
                        <Col span={24} style={{borderStyle:"ridge",marginTop:"5%",marginBottom:"2%",borderColor:"#F6F6F6"}}>
                        </Col>
                    </Row>
                    {/*--------------------------------------------------------------------------------*/}
                    <Form.Item>
                        <Row style={{marginBottom:"2%"}}>
                            <Col span={20} offset={2}>
                                <Button htmlType={"submit"} style={{background:"#CFD2CF",width:"100%",paddingBottom:"10%"}}>
                                    <p  className={"positionForChar"} style={{fontWeight:"bolder",fontSize:"1.5vw",marginBottom:"50%"}}>
                                        Submit Review
                                    </p>
                                </Button>
                            </Col>
                        </Row>
                    </Form.Item>
                </Form>
            </Col>
        </Row>
    );
}