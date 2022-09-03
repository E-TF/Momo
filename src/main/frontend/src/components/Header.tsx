import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../store";
import {Button, PageHeader} from "antd";
import {useNavigate} from "react-router-dom";
import {AuthState, logout, updateAccessToken, updateAuth} from "../slices/authSlice";
import axios from "axios";
import {useEffect, useState} from "react";
import UserMenu from "./UserMenu";

export interface UserInfo {
    id: number | null,
    name: string | null,
    imageUrl: string | null
}

const Header = (): JSX.Element => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    dispatch(updateAuth());

    const auth = useSelector((state: RootState) => state.auth);
    const [userInfo, setUserInfo] = useState<UserInfo>({id: null, name: null, imageUrl: null});
    const [authState, setAuthState] = useState<AuthState>(auth);

    useEffect(() => {
        if (authState.accessToken !== null) { //login 한 경우
            axios.get('/api/members/my-simple-info', {
                headers: {
                    'Authorization': `Bearer ${auth.accessToken as string}`
                }
            })
                .then(result => {
                    const receivedData: UserInfo = result.data as UserInfo;
                    setUserInfo(receivedData);
                })
                .catch(error => {
                    let status = error.response.status as number;
                    if (status === 401) {
                        axios.get('/api/tokens/refresh', {
                            headers: {
                                'refresh-token': `Bearer ${auth.refreshToken as string}`
                            }
                        })
                            .then(result => {
                                dispatch(updateAccessToken(result.headers['authorization']));
                                setAuthState({...authState, accessToken: result.headers['authorization']});
                            })
                            .catch(() => {
                                dispatch(logout());
                                navigate('/login');
                            })
                    }
                })
        }
    }, [authState]);

    return (
        <PageHeader title="Momo" subTitle="모두의 모임" extra={
            auth.isLoggedIn ? <UserMenu userInfo={userInfo}/> : <LoginBtn/>}/>
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