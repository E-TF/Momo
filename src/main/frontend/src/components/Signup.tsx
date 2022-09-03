import {useNavigate} from "react-router-dom";
import {Button, Form, Input, message} from "antd";
import React, {useState} from "react";
import axios from "axios";

interface SignupData {
    loginId: string,
    password: string,
    name: string,
    email: string,
    phoneNumber: string
}

function Signup(): JSX.Element {
    const navigate = useNavigate();
    const [disabled, setDisabled] = useState<boolean>(false);
    const [signupData, setSignupData] = useState<SignupData>({
        loginId: "",
        password: "",
        email: "",
        name: "",
        phoneNumber: ""
    });

    const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        setSignupData({...signupData, [name]: value});
    }

    const onFinish = () => {
        axios.post('/api/signup', signupData)
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
            {params: {loginId: signupData.loginId}})
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
        <>
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
                        <Input name={"loginId"} disabled={disabled}
                               style={{textAlign: 'left', width: 'calc(100% - 144.8px)'}}
                               placeholder="input Login ID"
                               onChange={(e) => {
                                   onChange(e)
                               }} required={true}/>
                        <Button onClick={() => {
                            onDuplicateCheck()
                        }} disabled={disabled}>Duplicate Check</Button>
                    </Input.Group>
                </Form.Item>
                <Form.Item label="Password" required>
                    <Input name={"password"} placeholder="input Password" type="password" required={true}
                           onChange={(e) => {
                               onChange(e)
                           }}/>
                </Form.Item>
                <Form.Item label="Name" required>
                    <Input name={"name"} placeholder="input Name" required={true}
                           onChange={(e) => {
                               onChange(e)
                           }}/>
                </Form.Item>
                <Form.Item label="Email" required>
                    <Input name={"email"} placeholder="example@exam.com" type={"email"} required={true}
                           onChange={(e) => {
                               onChange(e)
                           }}/>
                </Form.Item>
                <Form.Item label="Phone Number" required>
                    <Input name={"phoneNumber"} placeholder="+82-10-XXXX-XXXX" size={"large"} type={"tel"}
                           required={true}
                           onChange={(e) => {
                               onChange(e)
                           }}/>
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit" size={"large"} block disabled={!disabled}>Sign
                        Up</Button>
                </Form.Item>
                <Form.Item>
                    <Button size={"large"} block onClick={() => {
                        navigate(-1)
                    }}>Cancel</Button>
                </Form.Item>
            </Form>
        </>
    );
}

export default Signup;