import { useNavigate } from 'react-router-dom';
import {
    useAppDispatch,
    useAppSelector,
    useLocalStorage
} from '@renderer/hooks';
import { useEffect } from 'react';
import { parseJwt } from '@renderer/utils';
import { loggedIn } from '@renderer/store/slices/auth/authSlice';
import logo from '@renderer/assets/logo.png';
import { UserInfo } from '@renderer/components';

const HomePage = () => {
    const navigate = useNavigate();
    const user = useAppSelector((state) => state.auth.value);
    const dispatch = useAppDispatch();

    useEffect(() => {
        const { getItem } = useLocalStorage('access-token');

        const accessToken = getItem();

        if (!accessToken) navigate('/auth/login');
        const { id, sub, roles, avatar } = parseJwt(accessToken);

        dispatch(
            loggedIn({
                value: {
                    id: id,
                    username: sub,
                    email: '',
                    phoneNo: '',
                    roles: roles,
                    avatar: avatar
                }
            })
        );

        console.log('user: ', user);
    }, []);

    return (
        <div className="p-10 w-full">
            <div>
                <UserInfo />
                <div className="flex items-center gap-2 mb-5">
                    <div className="w-[30px] h-[30px]">
                        <img
                            src={logo}
                            className="w-full h-full object-cover"
                        />
                    </div>
                    <h1 className="font-semibold text-2xl text-sky-800">
                        Nông sản Việt
                    </h1>
                </div>
                <p className="text-sky-800 text-lg font-semibold">
                    Hệ thống quản lý kho thông minh
                </p>
            </div>
        </div>
    );
};

export default HomePage;
