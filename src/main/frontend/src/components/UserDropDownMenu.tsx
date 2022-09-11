import {Menu} from "antd";
import {useDispatch} from "react-redux";
import {useNavigate} from "react-router-dom";
import {logout} from "../slices/authSlice";

const UserDropDownMenu = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    return (
        <Menu
            items={[
                {
                    key: '1',
                    label: (
                        <a target="_blank" rel="noopener noreferrer" href="">
                            My Page
                        </a>
                    ),
                },
                {
                    key: '2',
                    label: (
                        <a target="_blank" rel="noopener noreferrer" onClick={() => {
                            dispatch(logout());
                            navigate('/');
                        }}>
                            Log Out
                        </a>
                    ),
                },
            ]}
        />
    );
};

export default UserDropDownMenu;