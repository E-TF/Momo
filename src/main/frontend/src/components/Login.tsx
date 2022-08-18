import {useNavigate} from "react-router-dom";
import {Button, Form, Input, message} from "antd";
import React from "react";
import axios, {AxiosResponseHeaders} from "axios";
import {login, Tokens} from "../slices/authSlice";
import {useDispatch} from "react-redux";
import {GithubOutlined, GoogleOutlined} from "@ant-design/icons";
import {AppDispatch} from "../store";

interface LoginRequest {
    loginId: string,
    password: string
};

function Login(): JSX.Element {
    const navigate = useNavigate();
    const dispatch = useDispatch<AppDispatch>();

    const loginRequestData: LoginRequest = {loginId: "", password: ""};
    const tokens: Tokens = {accessToken: '', refreshToken: ''};
    const popupWindowFeature:string = 'top=30,left=30,width=700,height=600,status=no,menubar=no,toolbar=no,resizable=no';

    const saveTokens = (headers: AxiosResponseHeaders) => {
        tokens.accessToken = headers['authorization'];
        tokens.refreshToken = headers['refresh-token'];
        dispatch(login(tokens));
        navigate('/');
    };

    const openOauthPopup = (registrationId:string) => {
        window.open('/oauth2/authorization/' + registrationId, registrationId + 'Window', popupWindowFeature);
    };

    const onChangeLoginId = (e: React.ChangeEvent<HTMLInputElement>) => {
        loginRequestData.loginId = e.currentTarget.value;
    };

    const onChangePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
        loginRequestData.password = e.currentTarget.value;
    };

    const onFinish = () => {
        axios.post('/api/login', loginRequestData)
            .then((result) => {
                saveTokens(result.headers)
            })
            .catch((error) => {
                message.error(error.response.data.message);
            })
    };

    return (
        <div>
            <div className="title">모두의 모임</div>
            <Form
                name="basic"
                layout='vertical'
                labelCol={{offset: 8}}
                wrapperCol={{offset: 8, span: 8}}
                onFinish={onFinish}
                autoComplete="on"
                size={"large"}
            >
                <Form.Item label="Login ID" required>
                    <Input placeholder="input Login ID" required={true}
                           onChange={(e) => {onChangeLoginId(e)}}/>
                </Form.Item>
                <Form.Item label="Password" required>
                    <Input placeholder="input Password" type="password" required={true}
                           onChange={(e) => {onChangePassword(e)}}/>
                </Form.Item>
                <Form.Item style={{marginTop: 40}}>
                    <Button type="primary" htmlType="submit" block>Log In</Button>
                </Form.Item>
                <Form.Item>
                    <Button block onClick={() => {navigate('/signup')}}>Sign Up</Button>
                </Form.Item>
                <Form.Item>
                    <Button style={{backgroundColor: "lightgray"}} onClick={() => {openOauthPopup('github')}}
                            block><GithubOutlined/> Login with Github</Button>
                </Form.Item>
                <Form.Item>
                    <Button style={{backgroundColor: "lightgray"}} block><GoogleOutlined/> Login with Google</Button>
                </Form.Item>
            </Form>
        </div>
    );
};

export default Login;