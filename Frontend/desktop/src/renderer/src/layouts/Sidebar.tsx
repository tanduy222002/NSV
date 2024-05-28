import { LuWarehouse } from 'react-icons/lu';
import { GoPackageDependencies } from 'react-icons/go';
import { GoPackageDependents } from 'react-icons/go';
// import { AiOutlineFundProjectionScreen } from 'react-icons/ai';
import { GiFruitBowl } from 'react-icons/gi';
import { CiSettings } from 'react-icons/ci';
import { LuHelpCircle } from 'react-icons/lu';
import { MdOutlineLogout } from 'react-icons/md';
import { GrUserWorker } from 'react-icons/gr';
import { RiUserSettingsLine } from 'react-icons/ri';
import { Outlet, useNavigate } from 'react-router-dom';
import SidebarSection from './SidebarSection';
import warehouseSrc from '@renderer/assets/warehouse.png';
import logoSrc from '@renderer/assets/logo.png';
import { useAppDispatch, useAppSelector } from '@renderer/hooks';
import { loggedOut } from '@renderer/store/slices/auth/authSlice';
import { logout } from '@renderer/services/api';
import { useLocalStorage } from '@renderer/hooks';
import { UserRole } from '@renderer/types/auth';
import { useMutation } from '@tanstack/react-query';
import { Loading } from '@renderer/components';

const Sidebar = () => {
    const user = useAppSelector((state) => state.auth.value);

    const firstSectionItems = [
        {
            title: 'Kho hàng',
            icon: <LuWarehouse />,
            path: '/warehouse'
        },
        {
            title: 'Nhập kho',
            icon: <GoPackageDependencies />,
            path: '/import'
        },
        {
            title: 'Xuất kho',
            icon: <GoPackageDependents />,
            path: '/export'
        },
        // {
        //     title: 'Thống kê',
        //     icon: <AiOutlineFundProjectionScreen />,
        //     path: '/statistic'
        // },
        {
            title: 'Sản phẩm',
            icon: <GiFruitBowl />,
            path: '/product'
        },
        {
            title: 'Đối tác',
            icon: <RiUserSettingsLine />,
            path: '/partner'
        },
        {
            title: 'Nhân viên',
            icon: <GrUserWorker />,
            path: '/employee',
            show: user?.roles?.includes(UserRole.Admin)
        }
    ];

    const secondSectionItems = [
        {
            title: 'Cài đặt',
            icon: <CiSettings />,
            path: '/setting'
        },
        {
            title: 'Trợ giúp',
            icon: <LuHelpCircle />,
            path: '/help'
        }
    ];

    const navigate = useNavigate();
    const dispatch = useAppDispatch();
    const { deleteItem: deleteAccessToken } = useLocalStorage('access-token');
    const { deleteItem: deleteRefreshToken } = useLocalStorage('refresh-token');

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const logoutMutation = useMutation({
        mutationFn: async (payload: any) => {
            const response = await logout(payload);
            return response;
        }
    });

    const loggout = async () => {
        await logoutMutation.mutateAsync({
            token: accessToken
        });
        navigate('/auth/login');
        deleteAccessToken();
        deleteRefreshToken();
        dispatch(loggedOut());
    };

    return (
        <div className="flex">
            {logoutMutation.isPending && <Loading />}
            <div className="flex flex-col items-center w-[280px] h-screen sticky top-0 py-8 border-r border-gray-200">
                <div className="flex items-center gap-2 my-8">
                    <div>
                        <img
                            className="w-[40px] h-[30px]"
                            src={warehouseSrc}
                            alt="warehouse image"
                        />
                    </div>
                    <div>
                        <img
                            className="w-[20px] h-[20px]"
                            src={logoSrc}
                            alt="logo"
                        />
                    </div>
                    <p className="font-semibold text-2xl">NSV</p>
                </div>
                <div className="flex flex-col gap-4">
                    {firstSectionItems.map(({ title, icon, path, show }) => (
                        <SidebarSection
                            key={title}
                            title={title}
                            icon={icon}
                            path={path}
                            show={show}
                        />
                    ))}
                </div>
                <div className="flex flex-col gap-4 mt-auto">
                    {secondSectionItems.map(({ title, icon, path }) => (
                        <SidebarSection
                            key={title}
                            title={title}
                            icon={icon}
                            path={path}
                        />
                    ))}
                    <button
                        className=" flex items-center gap-2 font-semibold text-red-500 border-none"
                        onClick={loggout}
                    >
                        <MdOutlineLogout />
                        <p>Đăng xuất</p>
                    </button>
                </div>
            </div>
            <Outlet />
        </div>
    );
};

export default Sidebar;
