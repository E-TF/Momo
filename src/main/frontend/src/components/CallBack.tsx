import {Spin} from 'antd';
import React from 'react';
import {useParams} from "react-router-dom";
import {useDispatch} from "react-redux";
import {login, Tokens} from "../slices/authSlice";

function CallBack(): JSX.Element {
    const {accessToken, refreshToken} = useParams();
    const dispatch = useDispatch();
    const tokens:Tokens = {accessToken: accessToken as string, refreshToken:refreshToken as string};

    dispatch(login(tokens));
    window.opener.location.replace('/');
    window.close();

    return (
        <Spin size={"large"} style={{paddingTop:"250px"}} tip="Loading...">
        </Spin>
    );
};

export default CallBack;