import { MdKeyboardArrowDown } from 'react-icons/md';
import {
    useAppDispatch,
    useAppSelector,
    useLocalStorage
} from '@renderer/hooks';
import { GoBell } from 'react-icons/go';
import { useState } from 'react';
import { IoLogOutOutline } from 'react-icons/io5';
import { CgProfile } from 'react-icons/cg';
import { useNavigate } from 'react-router-dom';
import { useMutation } from '@tanstack/react-query';
import { logout } from '@renderer/services/api';
import { loggedOut } from '@renderer/store/slices/auth/authSlice';
import Loading from './Loading';
import { RxAvatar } from 'react-icons/rx';

const UserInfo = () => {
    const navigate = useNavigate();
    const goToUserProfilePage = () => navigate('/profile');
    const [open, setOpen] = useState(false);
    const toggleOpen = () => setOpen((prev) => !prev);
    const user = useAppSelector((state) => state.auth.value);

    const dispatch = useAppDispatch();

    const { getItem, deleteItem: deleteAccessToken } =
        useLocalStorage('access-token');
    const { deleteItem: deleteRefreshToken } = useLocalStorage('refresh-token');
    const accessToken = getItem();

    const logoutMutation = useMutation({
        mutationFn: async (payload: any) => {
            const response = await logout(payload);
            return response;
        }
    });

    const handleLogout = async () => {
        await logoutMutation.mutateAsync({
            token: accessToken
        });
        navigate('/auth/login');
        deleteAccessToken();
        deleteRefreshToken();
        dispatch(loggedOut());
    };

    return (
        <div
            className="flex items-center gap-5 ml-auto w-fit relative"
            tabIndex={0}
            onBlur={() => setOpen(false)}
        >
            {logoutMutation.isPending && <Loading />}
            <GoBell
                data-testid="bell-icon"
                className="w-[20px] h-[20px] cursor-pointer"
            />
            <div className="flex items-center gap-1 border border-sky-800 rounded-md font-semibold px-2 py-1 text-sky-800">
                <div className="w-[36px] h-[36px] rounded-full overflow-hidden flex items-center">
                    {user?.avatar ? (
                        <img
                            className="w-full h-full cover"
                            src={user?.avatar}
                        />
                    ) : (
                        <RxAvatar className="h-full" />
                    )}
                </div>
                <p>{user?.username}</p>
                <MdKeyboardArrowDown
                    data-testid="arrow-icon"
                    className="w-[24px] h-[24px] cursor-pointer text-gray-300 hover:text-sky-800"
                    onClick={toggleOpen}
                />
            </div>
            {open && (
                <div
                    className="absolute border border-gray-300 rounded-md w-fit top-12 right-0"
                    onClick={() => setOpen(false)}
                >
                    <div
                        onClick={() => goToUserProfilePage()}
                        className="border-b border-gray-300 flex items-center gap-2 p-2 bg-white text-sky-800 cursor-pointer hover:bg-sky-50"
                    >
                        <CgProfile />
                        <p className="text-base font-semibold">Cá nhân</p>
                    </div>
                    <div
                        onClick={() => handleLogout()}
                        className="flex items-center gap-2 p-2 text-sky-800 hover:text-red-500 bg-white cursor-pointer hover:bg-red-50"
                    >
                        <IoLogOutOutline />
                        <p className="text-base font-semibold">Đăng xuất</p>
                    </div>
                </div>
            )}
        </div>
    );
};

export default UserInfo;
