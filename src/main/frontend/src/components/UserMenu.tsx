import {UserInfo} from "./Header";
import {Dropdown, Space} from "antd";
import {DownOutlined} from "@ant-design/icons";
import UserProfile from "./UserProfile";
import UserDropDownMenu from "./UserDropDownMenu";

export type UserProps = {
    userInfo: UserInfo;
}

const UserMenu = ({userInfo}: UserProps): JSX.Element => {

    return (
        <>
            <Dropdown overlay={UserDropDownMenu}>
                <a onClick={e => e.preventDefault()}>
                    <Space>
                        <UserProfile userInfo={userInfo}/>
                        <DownOutlined/>
                    </Space>
                </a>
            </Dropdown>
        </>
    )
};

export default UserMenu;