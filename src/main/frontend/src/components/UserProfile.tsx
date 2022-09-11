import {Avatar, Space} from "antd";
import {UserInfoProps} from "./UserMenu";

const UserProfile = ({userInfo}: UserInfoProps): JSX.Element => {
    return (
        <Space>
            <Avatar src={userInfo.imageUrl}/>
            <div>{userInfo.name}님</div>
        </Space>
    )
};

export default UserProfile;