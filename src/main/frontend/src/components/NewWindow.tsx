import {useParams} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {AuthState, login, Tokens} from "../slices/authSlice";
import {RootState} from "../store";

function NewWindow():JSX.Element {
    localStorage.setItem("accessToken", "accessToken Data");
    localStorage.setItem("refreshToken", "refreshToken Data");
    const {accessToken, refreshToken} = useParams();
    console.log(accessToken);
    console.log(refreshToken);
    const data1:string = 'data1 from new window';
    console.log('hi start');
    const message = '1234'
    window.parent.postMessage(message, "*");
    console.log('hi end');
    const data2:string = 'data2 from new window';
    const dispatch = useDispatch();
    const tokens:Tokens = {
        accessToken: data1,
        refreshToken: data2
    }
    window.opener.location.replace('/');
    //여기서 부모에게 데이터 보내면 되는데..
    dispatch(login(tokens));
    let auth = useSelector((state: RootState) => state.auth) as AuthState;

    console.log(auth);
    return (
        <>
            hello
        </>
    )
}

export default NewWindow;