import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../store";
import {Button, PageHeader} from "antd";
import {useNavigate} from "react-router-dom";
import {login, Tokens} from "../slices/authSlice";
import axios from "axios";
import {useEffect, useState} from "react";
import UserMenu from "./UserMenu";

export interface UserInfo {
    id: number | null,
    name: string | null,
    imageUrl: string | null
};

const Header = (): JSX.Element => {
    const dispatch = useDispatch();
    const auth = useSelector((state: RootState) => state.auth);

    const accessToken = localStorage.getItem("accessToken");
    const refreshToken = localStorage.getItem("refreshToken");

    if (accessToken !== null && refreshToken !== null) {
        const tokens: Tokens = {
            accessToken: accessToken as string,
            refreshToken: refreshToken as string
        };
        dispatch(login(tokens));
    }

    const [userInfo, setUserInfo] = useState<UserInfo>({id: 0, name: null, imageUrl: null});

    useEffect(() => {
        axios.get('/api/members/my-simple-info', {
            headers: {
                'Authorization': `Bearer ${auth.accessToken as string}`
            }
        })
            .then(result => {
                const receivedData = result.data;
                setUserInfo(receivedData);
            })
    }, []);

    return (
        <>
            <PageHeader title="Momo" subTitle="모두의 모임" extra={
                auth.isLoggedIn ? <UserMenu userInfo={userInfo}/> : <LoginBtn/>}></PageHeader>
        </>
    );
};

const LoginBtn = (): JSX.Element => {
    let navigate = useNavigate();
    return (
        <Button onClick={() => {
            navigate('/login')
        }} type='primary'>로그인</Button>
    );
};

export default Header;