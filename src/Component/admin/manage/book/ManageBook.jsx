import React from "react";
import './ManageBook.css';
import {Col, Menu, Row} from "antd";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPeopleRoof, faSquarePlus} from "@fortawesome/free-solid-svg-icons";
import '../user/ManageCustomer.css';
import ManageBookTable from "./table/ManageBookTable";

export default function ManageBook(){

    return(
        <div className="book-container">
            <Row>
                <Col span={5}>
                    <div>
                        <div className="logo-placeholder">
                            <img className="logo-container" src="https://s.tmimgcdn.com/scr/1200x750/242600/book-shop-modern-logo-template_242604-original.jpg"/>
                        </div>
                        <div >
                            <Menu mode="inline" className="manage-user-menu">
                                <Menu.Item icon={<FontAwesomeIcon icon={faPeopleRoof}/>} >Manage</Menu.Item>
                                <Menu.Item  icon={<FontAwesomeIcon icon={faSquarePlus}/>}>Create</Menu.Item>
                            </Menu>
                        </div>
                    </div>
                </Col>
                <Col span={19}>
                    <ManageBookTable/>
                </Col>
            </Row>
        </div>
    )

}