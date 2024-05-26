import { Button, DataField, PageLoading, UserInfo } from '@renderer/components';
import { AccountRoleText } from '@renderer/constants/employee';
import { useAppSelector, useLocalStorage } from '@renderer/hooks';
import { getEmployeeDetail } from '@renderer/services/api';
import { useQuery } from '@tanstack/react-query';
import { FaPhone, FaUserCog } from 'react-icons/fa';
import { IoChevronBack, IoLocationOutline } from 'react-icons/io5';
import { MdOutlineEmail } from 'react-icons/md';
import { useNavigate } from 'react-router-dom';
import { MdOutlineAccountBox } from 'react-icons/md';
import { FaEdit } from 'react-icons/fa';
import { RxAvatar } from 'react-icons/rx';
import { FaGenderless } from 'react-icons/fa6';

const UserProfilePage = () => {
    const user = useAppSelector((state) => state.auth.value);

    const navigate = useNavigate();
    const goToWarehoussePage = () => navigate('/warehouse');
    const goToEditUserProfilePage = () => navigate('/profile/edit');

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const { data, isFetching } = useQuery({
        queryKey: ['user', user?.id],
        queryFn: () =>
            getEmployeeDetail({
                token: accessToken,
                employeeId: user!.id.toString()
            })
    });

    if (!isFetching) console.log(data);

    return (
        <div className="w-full px-5 py-5">
            <UserInfo />
            <div className="flex items-center gap-2 mb-5">
                <IoChevronBack
                    className="text-blue-800 h-[30px] w-[30px] px-1 py-1 cursor-pointer hover:bg-blue-50 rounded-full"
                    onClick={goToWarehoussePage}
                />
                {/* <img alt="form-icon" src={partnerIconSrc} /> */}
                <h1 className="text-xl font-semibold">Thông tin cá nhân</h1>
            </div>
            {isFetching ? (
                <PageLoading />
            ) : (
                <div className="space-y-5 flex-1 max-w-[600px]">
                    <div className="w-[200px] h-[200px] p-3 my-3 border border-sky-800 rounded-md mx-auto flex items-center justify-center">
                        {data?.avatar ? (
                            <img
                                src={data?.avatar}
                                className="w-full h-full object-cover"
                            />
                        ) : (
                            <RxAvatar className="w-full h-full min-[400px] text-gray-300" />
                        )}
                    </div>
                    <DataField
                        name="Tên đối tác"
                        icon={<FaUserCog />}
                        disabled={false}
                        value={data?.name}
                        defaultValue="Tên đối tác"
                    />
                    <DataField
                        name={'Số điện thoại'}
                        icon={<FaPhone />}
                        disabled={false}
                        value={data?.phone_number}
                        defaultValue={'Số điện thoại'}
                    />
                    <DataField
                        name={'Email'}
                        icon={<MdOutlineEmail />}
                        disabled={false}
                        value={data?.email}
                        defaultValue={'Email'}
                    />
                    <DataField
                        name={'Địa chỉ'}
                        icon={<IoLocationOutline />}
                        disabled={false}
                        value={data?.address}
                        defaultValue={'Địa chỉ'}
                    />
                    <DataField
                        name={'Giới tính'}
                        icon={<FaGenderless />}
                        disabled={false}
                        value={data?.gender === 'M' ? 'Nam' : 'Nữ'}
                        defaultValue={'Nam'}
                    />
                    <DataField
                        name={'Loại tài khoản '}
                        icon={<MdOutlineAccountBox />}
                        disabled={false}
                        value={AccountRoleText[data?.roles?.[0]]}
                        defaultValue={'Địa chỉ'}
                    />
                    <Button
                        icon={
                            <FaEdit className="w-[20px] h-[20px] text-amber-300" />
                        }
                        className="mx-auto px-2 py-1 border rounded-md border-amber-300 text-amber-300 hover:bg-amber-50 font-semibold w-fit"
                        text="Cập nhật"
                        action={goToEditUserProfilePage}
                    />
                </div>
            )}
        </div>
    );
};

export default UserProfilePage;
