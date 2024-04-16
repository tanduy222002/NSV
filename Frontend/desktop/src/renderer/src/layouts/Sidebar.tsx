import { LuWarehouse } from 'react-icons/lu';
import { GoPackageDependencies } from 'react-icons/go';
import { GoPackageDependents } from 'react-icons/go';
import { AiOutlineFundProjectionScreen } from 'react-icons/ai';
import { GiFruitBowl } from 'react-icons/gi';
import { CiSettings } from 'react-icons/ci';
import { LuHelpCircle } from 'react-icons/lu';
import { MdOutlineLogout } from 'react-icons/md';
import { RiUserSettingsLine } from 'react-icons/ri';
import { Outlet, useNavigate } from 'react-router-dom';
import SidebarSection from './SidebarSection';
import warehouseSrc from '@renderer/assets/warehouse.png';
import logoSrc from '@renderer/assets/logo.png';
import { useAppDispatch } from '@renderer/hooks';
import { loggedOut } from '@renderer/store/slices/auth/authSlice';
import { useLocalStorage } from '@renderer/hooks';

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
    {
        title: 'Thống kê',
        icon: <AiOutlineFundProjectionScreen />,
        path: '/statistic'
    },
    {
        title: 'Sản phẩm',
        icon: <GiFruitBowl />,
        path: '/product'
    },
    {
        title: 'Khách hàng',
        icon: <RiUserSettingsLine />,
        path: '/customer'
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

const Sidebar = () => {
    const navigate = useNavigate();
    const dispatch = useAppDispatch();
    const { deleteItem: deleteAccessToken } = useLocalStorage('access-token');
    const { deleteItem: deleteRefreshToken } = useLocalStorage('refresh-token');

    const loggout = () => {
        navigate('/auth/login');
        deleteAccessToken();
        deleteRefreshToken();
        dispatch(loggedOut());
    };

    return (
        <div className="flex">
            <div className="flex flex-col items-center w-[280px] h-screen py-8 border-r border-gray-200">
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
                    {firstSectionItems.map(({ title, icon, path }) => (
                        <SidebarSection
                            key={title}
                            title={title}
                            icon={icon}
                            path={path}
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
