import {Avatar, Space} from "antd";
import {UserProps} from "./UserMenu";

const UserProfile = ({userInfo}: UserProps): JSX.Element => {
    return (
        <Space>
            <Avatar src={userInfo.imageUrl}/>
            <div>{userInfo.name}님</div>
        </Space>
    )
};

export default UserProfile;