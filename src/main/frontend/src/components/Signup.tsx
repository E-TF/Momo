import {useNavigate} from "react-router-dom";
import {Button, Form, Input, message, Tooltip} from "antd";
import React, {useState} from "react";
import axios from "axios";

interface SignupRequest {
    loginId: string,
    password: string,
    name: string,
    email: string,
    phoneNumber: string
}

function Signup(): JSX.Element {
    const navigate = useNavigate();
    let [disabled, setDisabled] = useState(false);
    const signupRequestData: SignupRequest = {loginId: "", password: "", email: "", name: "", phoneNumber: ""}

    const onLoginIdChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        signupRequestData.loginId = e.currentTarget.value;
    }

    const onPasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        signupRequestData.password = e.currentTarget.value;
    }

    const onNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        signupRequestData.name = e.currentTarget.value;
    }

    const onEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        signupRequestData.email = e.currentTarget.value;
    }

    const onPhoneNumberChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        signupRequestData.phoneNumber = e.currentTarget.value;
    }

    const onFinish = () => {
        axios.post('/api/signup', signupRequestData)
            .then(() => {
                navigate('/');
                message.success('success');
            })
            .catch((error) => {
                message.error(error.response.data.message);
            });
    }

    const onDuplicateCheck = () => {
        axios.get('/api/signup/loginid/duplicate',
            {params: {loginId: signupRequestData.loginId}})
            .then(() => {
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
    }

    return (
        <div>
            <div className="title">모두의 모임</div>
            <Form
                name="basic"
                layout='vertical'
                labelCol={{offset: 8}}
                wrapperCol={{offset: 8, span: 8}}
                size={"large"}
                onFinish={onFinish}
            >
                <Form.Item label="Login ID" required>
                    <Input.Group compact>
                        <Input disabled={disabled} style={{textAlign: 'left', width: 'calc(100% - 144.8px)'}}
                               placeholder="input Login ID"
                               onChange={(e) => {
                                   onLoginIdChange(e)
                               }} required={true}/>
                        <Button onClick={() => {
                            onDuplicateCheck()
                        }}>Duplicate Check</Button>
                    </Input.Group>
                </Form.Item>
                <Form.Item label="Password" required>
                    <Input placeholder="input Password" type="password" required={true}
                           onChange={(e) => {
                               onPasswordChange(e)
                           }}/>
                </Form.Item>
                <Form.Item label="Name" required>
                    <Input placeholder="input Name" required={true}
                           onChange={(e) => {
                               onNameChange(e)
                           }}/>
                </Form.Item>
                <Form.Item label="Email" required>
                    <Input placeholder="example@exam.com" type={"email"} required={true}
                           onChange={(e) => {
                               onEmailChange(e)
                           }}/>
                </Form.Item>
                <Form.Item label="Phone Number" required>
                    <Input placeholder="+82-10-XXXX-XXXX" size={"large"} type={"tel"} required={true}
                           onChange={(e) => {
                               onPhoneNumberChange(e)
                           }}/>
                </Form.Item>
                <Form.Item>
                    <Tooltip placement="top" title={<span>do duplicate check first!!</span>}>
                        <Button type="primary" htmlType="submit" size={"large"} block disabled={!disabled}>Sign
                            Up</Button>
                    </Tooltip>
                </Form.Item>
                <Form.Item>
                    <Button size={"large"} block onClick={() => {
                        navigate(-1)
                    }}>Cancel</Button>
                </Form.Item>
            </Form>
        </div>
    )
}

export default Signup;