import {
    AddressPickerController,
    Button,
    ConfirmationPopup,
    FileInput,
    FormInput,
    InformationPopup,
    Loading,
    ModalProvider,
    SelectInput,
    UserInfo
} from '@renderer/components';
import { useAppSelector, useLocalStorage } from '@renderer/hooks';
import {
    getEmployeeDetail,
    updateEmployeeProfile
} from '@renderer/services/api';
import { useMutation, useQuery } from '@tanstack/react-query';
import { FaPhone, FaUserCog } from 'react-icons/fa';
import { IoChevronBack } from 'react-icons/io5';
import { MdOutlineEmail } from 'react-icons/md';
import { useNavigate } from 'react-router-dom';
import { FaEdit } from 'react-icons/fa';
import { RxAvatar } from 'react-icons/rx';
import { useRef, useState } from 'react';
import { InfoPopup, ResultPopup } from '@renderer/types/common';
import {
    UpdateProfileResult,
    updateProfileConfirmPopupData
} from '@renderer/constants/employee';
import { Address } from '@renderer/types/partner';

const EditUserProfilePage = () => {
    const user = useAppSelector((state) => state.auth.value);

    const navigate = useNavigate();
    const goToUserProfilePage = () => navigate('/profile');

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const { data, isFetching } = useQuery({
        queryKey: ['user', user?.id],
        queryFn: async () => {
            const response = await getEmployeeDetail({
                token: accessToken,
                employeeId: user!.id.toString()
            });
            setFileSrc(response?.avatar);
            setAddress({
                address: data?.addresses?.name,
                ward: {
                    id: data?.addresses?.ward?.id,
                    name: data?.addresses?.ward?.name
                },
                district: {
                    id: data?.addresses?.ward?.district?.id,
                    name: data?.addresses?.ward?.district?.name
                },
                province: {
                    id: data?.addresses?.ward?.district?.province?.id,
                    name: data?.addresses?.ward?.district?.province?.name
                }
            });

            return response;
        }
    });

    const [resultPopup, setResultPopup] = useState<ResultPopup | null>(null);
    const closeResultPopup = () => setResultPopup(null);

    const [infoPopup, setInfoPopup] = useState<InfoPopup | null>(null);
    const closeInfoPopup = () => setInfoPopup(null);
    const openUpdateConfirm = () => setInfoPopup(updateProfileConfirmPopupData);

    const [address, setAddress] = useState<Address | null>(null);

    const [gender, setGender] = useState('M');
    const updateGender = (gender: string) => {
        if (gender === 'Nam') {
            setGender('M');
        } else {
            setGender('F');
        }
    };
    const [fileSrc, setFileSrc] = useState<string | undefined>(data?.avatar);

    const handleFileChange = (fileSrc: string) => {
        setFileSrc(fileSrc);
    };

    const nameRef = useRef<HTMLInputElement>(null);
    const phoneRef = useRef<HTMLInputElement>(null);
    const emailRef = useRef<HTMLInputElement>(null);

    const updateProfileMutation = useMutation({
        mutationFn: async (payload: any) => {
            const response = await updateEmployeeProfile({
                token: accessToken,
                employeeId: user!.id,
                profile: payload
            });
            return response;
        }
    });

    const handleUpdateProfile = async () => {
        closeInfoPopup();
        const response = await updateProfileMutation.mutateAsync({
            name: nameRef?.current?.value ?? data?.name,
            gender: gender ?? data?.gender,
            email: emailRef?.current?.value ?? data?.email,
            avatar: fileSrc !== data?.avatar ? fileSrc : undefined,
            phoneNumber: phoneRef?.current?.value ?? data?.phone_number,
            addresses: {
                address: address?.address,
                wardId: address?.ward?.id,
                districtId: address?.district?.id,
                provinceId: address?.province?.id
            }
        });

        if (response?.status === 200) {
            setResultPopup(UpdateProfileResult.Success);
            return;
        }
        if (response?.status >= 400) {
            setResultPopup(UpdateProfileResult.Error);
            return;
        }
    };

    if (!isFetching) console.log(data);

    return (
        <div className="w-full px-5 py-5">
            {updateProfileMutation.isPending && <Loading />}
            {infoPopup && (
                <ConfirmationPopup
                    title={infoPopup.title}
                    body={infoPopup.body}
                    confirmAction={handleUpdateProfile}
                    cancelAction={closeInfoPopup}
                />
            )}
            {resultPopup && (
                <InformationPopup
                    title={resultPopup.title}
                    body={resultPopup.body}
                    popupType={resultPopup.popupType}
                    closeAction={closeResultPopup}
                />
            )}
            <UserInfo />
            <div className="flex items-center gap-2 mb-5">
                <IoChevronBack
                    className="text-blue-800 h-[30px] w-[30px] px-1 py-1 cursor-pointer hover:bg-blue-50 rounded-full"
                    onClick={goToUserProfilePage}
                />
                <h1 className="text-xl font-semibold">
                    Chỉnh sửa thông tin cá nhân
                </h1>
            </div>
            <div className="space-y-5 flex-1 max-w-[600px]">
                <FileInput
                    fileSrc={fileSrc}
                    onChange={handleFileChange}
                    fallbackImage={
                        <RxAvatar className="w-full h-full min-[400px] text-gray-300" />
                    }
                />
                <FormInput
                    label="Tên đối tác"
                    name="Tên đối tác"
                    icon={<FaUserCog />}
                    defaultValue={data?.name}
                    bg="bg-white"
                    ref={nameRef}
                />
                <FormInput
                    label="Số điện thoại"
                    name="Số điện thoại"
                    icon={<FaPhone />}
                    defaultValue={data?.phone_number}
                    bg="bg-white"
                    ref={phoneRef}
                />
                <FormInput
                    label="Email"
                    name={'Email'}
                    icon={<MdOutlineEmail />}
                    defaultValue={data?.email}
                    bg="bg-white"
                    ref={emailRef}
                />
                <ModalProvider>
                    <AddressPickerController
                        address={address}
                        updateAddress={setAddress}
                    />
                </ModalProvider>
                <SelectInput
                    placeHolder="Giới tính"
                    values={['Nam', 'Nữ']}
                    selectedValue={gender === 'M' ? 'Nam' : 'Nữ'}
                    onSelect={updateGender}
                />

                <Button
                    icon={
                        <FaEdit className="w-[18px] h-[18px] text-emerald-500" />
                    }
                    className="mx-auto px-2 py-1 border rounded-md border-emerald-500 text-emerald-500 hover:bg-emerald-50 font-semibold w-fit"
                    text="Xác nhận"
                    action={openUpdateConfirm}
                />
            </div>
        </div>
    );
};

export default EditUserProfilePage;
