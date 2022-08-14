import {useNavigate} from "react-router-dom";
import {Button, Form, Input, message} from "antd";
import React, {useState} from "react";
import axios from "axios";

interface SignupRequest {
    loginId: string,
    password: string,
    name: string,
    email: string,
    phoneNumber: string
}

interface SignupCheckList {
    duplicateCheck: boolean
}

function Signup(): JSX.Element {
    let navigate = useNavigate();
    let signupRequestData: SignupRequest = {loginId: "", password: "", email: "", name: "", phoneNumber: ""}
    let signupCheckList: SignupCheckList = {duplicateCheck: false}
    let [disabled, setDisabled] = useState(false);
    return (
        <div>
            <div className="title">모두의 모임</div>
            <Form
                name="basic"
                layout='vertical'
                labelCol={{offset: 8}}
                wrapperCol={{offset: 8, span: 8}}
                size={"large"}
            >
                <Form.Item label="Login ID" required>
                    <Input.Group compact>
                        <Input disabled={disabled} style={{textAlign: 'left', width: 'calc(100% - 144.8px)'}}
                               placeholder="input Login ID"
                               onChange={(e) => {
                                   signupCheckList.duplicateCheck = false;
                                   signupRequestData.loginId = e.currentTarget.value;
                               }} required={true}/>
                        <Button onClick={() => {
                            axios.get('/api/signup/loginid/duplicate',
                                {params: {loginId: signupRequestData.loginId}})
                                .then((result) => {
                                    signupCheckList.duplicateCheck = true;
                                    setDisabled(true);
                                    message.success('duplicate check success!!');
                                })
                                .catch((error) => {
                                    let status = error.response.status as number;

                                    if (status === 400)
                                        message.error(error.response.data);
                                    else if (status === 409) {
                                        message.error(error.response.data.message);
                                    }
                                });
                        }}>Duplicate Check</Button>
                    </Input.Group>
                </Form.Item>
                <Form.Item label="Password" required>
                    <Input placeholder="input Password" type="password" required={true}
                           onChange={(e) => {
                               signupRequestData.password = e.currentTarget.value;
                           }}/>
                </Form.Item>
                <Form.Item label="Name" required>
                    <Input placeholder="input Login ID" required={true}
                           onChange={(e) => {
                               signupRequestData.name = e.currentTarget.value;
                           }}/>
                </Form.Item>
                <Form.Item label="Email" required>
                    <Input placeholder="example@exam.com" type={"email"} required={true}
                           onChange={(e) => {
                               signupRequestData.email = e.currentTarget.value;
                           }}/>
                </Form.Item>
                <Form.Item label="Phone Number" required>
                    <Input placeholder="input Login ID" size={"large"} type={"tel"} required={true}
                           onChange={(e) => {
                               signupRequestData.phoneNumber = e.currentTarget.value;
                           }}/>
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit" size={"large"} block onClick={() => {
                        axios.post('/api/signup', signupRequestData)
                            .then((result) => {
                                navigate('/');
                                message.success('success');
                            })
                            .catch((error) => {
                                message.error(error.response.data.message);
                            });
                    }}>Sign Up</Button>
                </Form.Item>
                <Form.Item>
                    <Button size={"large"} block onClick={() => {
                        navigate(-1);
                    }}>Cancel</Button>
                </Form.Item>
            </Form>
        </div>
    )
}

export default Signup;