import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../store";
import {Button, PageHeader, Space} from "antd";
import {useNavigate} from "react-router-dom";
import {login, logout, Tokens} from "../slices/authSlice";

function Header(): JSX.Element {
    let auth = useSelector((state: RootState) => state.auth);
    console.log(auth);
    const dispatch = useDispatch();
    let token = localStorage.getItem("accessToken");

    if (token !== null) {
        console.log("여기 왜들어옴?");
        const tokens: Tokens = {
            accessToken: token as string,
            refreshToken: localStorage.getItem("refreshToken") as string
        }
        dispatch(login(tokens));
    }
    return (
        <>
            <PageHeader title="Momo" subTitle="모두의 모임" extra={[
                auth.isLoggedIn ? <LogOutButtons/> : <LogInButtons/>]}></PageHeader>
        </>
    )
}

function LogInButtons(): JSX.Element {
    let navigate = useNavigate();
    const accessToken = localStorage.getItem("accessToken");
    console.log(accessToken);
    return (
        <Space>
            <Button onClick={() => {
                navigate('/login')
            }} type='primary'>로그인</Button>
            <Button onClick={() => {
                navigate('/signup')
            }}>회원가입</Button>
        </Space>
    )
}

function LogOutButtons(): JSX.Element {
    let dispatch = useDispatch();
    return (
        <>
            <Button onClick={() => {
                dispatch(logout());
            }}>로그아웃</Button>
        </>
    )
}

export default Header;