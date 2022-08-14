import {useNavigate} from "react-router-dom";
import {Button, Form, Input, message} from "antd";
import React from "react";
import axios from "axios";
import {login, Tokens} from "../slices/authSlice";
import {useDispatch} from "react-redux";
import {GithubOutlined, GoogleOutlined} from "@ant-design/icons";

interface LoginRequest {
    loginId: string,
    password: string
}

function Login(): JSX.Element {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const loginRequestData: LoginRequest = {loginId: "", password: ""};
    let tokens: Tokens;


    const onFinish = () => {
        axios.post('/api/login', loginRequestData)
            .then((result) => {
                tokens = {
                    accessToken: result.headers['authorization'],
                    refreshToken: result.headers['refresh-token']
                }
                dispatch(login(tokens));
                navigate('/');
            })
            .catch((error) => {
                message.error(error.response.data.message);
            })
    }

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
                           onChange={(e) => {
                               loginRequestData.loginId = e.currentTarget.value;
                           }}/>
                </Form.Item>
                <Form.Item label="Password" required>
                    <Input placeholder="input Password" type="password" required={true}
                           onChange={(e) => {
                               loginRequestData.password = e.currentTarget.value;
                           }}/>
                </Form.Item>
                <Form.Item style={{marginTop: 40}}>
                    <Button type="primary" htmlType="submit" block>Log In</Button>
                </Form.Item>
                <Form.Item>
                    <Button block onClick={() => {
                        navigate('/signup');
                    }}>Sign Up</Button>
                </Form.Item>
                <Form.Item>
                    <Button style={{backgroundColor: "lightgray"}} onClick={()=>{
                        const win:Window = window.open('/oauth2/authorization/github') as Window;
                        window.open('/newWindow/123/456');
                    }} block
                    ><GithubOutlined/> Login with Github</Button>
                </Form.Item>
                <Form.Item>
                    <Button style={{backgroundColor: "lightgray"}} block><GoogleOutlined/> Login with Google</Button>
                </Form.Item>
            </Form>
        </div>
    );
}

export function setData(data:string){
    const dataFromNewWindow = data;
    console.log(dataFromNewWindow);
}

export default Login;