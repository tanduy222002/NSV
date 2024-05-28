import {
    AddressPickerController,
    Button,
    ConfirmationPopup,
    FileInput,
    FormInput,
    InformationPopup,
    Loading,
    ModalProvider,
    UserInfo
} from '@renderer/components';
import { IoChevronBack } from 'react-icons/io5';
import { useNavigate, useParams } from 'react-router-dom';
import partnerIconSrc from '@renderer/assets/partner-icon.png';
import { useLocalStorage } from '@renderer/hooks';
import { useMutation, useQuery } from '@tanstack/react-query';
import { getPartnerDetail, updatePartnerProfile } from '@renderer/services/api';
import { RxAvatar } from 'react-icons/rx';
import { FaMoneyCheckAlt, FaPhone, FaUserCog } from 'react-icons/fa';
import { MdOutlineEmail } from 'react-icons/md';
import { TbPigMoney } from 'react-icons/tb';
import { FaFax } from 'react-icons/fa';
import { useRef, useState } from 'react';
import {
    Address,
    InfoPopup,
    ResultPopup,
    ResultPopupType
} from '@renderer/types/common';
import {
    UpdatePartnerProfileResult,
    updatePartnerProfileConfirmPopupData
} from '@renderer/constants/partner';

const EditPartnerDetailPage = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const goToPartnerDetailPage = () => navigate(`/partner/${id}`);

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const { data, isFetching } = useQuery({
        queryKey: ['partner', id],
        queryFn: async () => {
            const response = await getPartnerDetail({
                token: accessToken,
                partnerId: id as string
            });
            setFileSrc(response?.avatar);
            setAddress({
                address: data?.address?.name,
                ward: {
                    id: data?.address?.ward?.id,
                    name: data?.address?.ward?.name
                },
                district: {
                    id: data?.address?.ward?.district?.id,
                    name: data?.address?.ward?.district?.name
                },
                province: {
                    id: data?.address?.ward?.district?.province?.id,
                    name: data?.address?.ward?.district?.province?.name
                }
            });
            return response;
        }
    });

    const nameRef = useRef<HTMLInputElement>(null);
    const phoneRef = useRef<HTMLInputElement>(null);
    const emailRef = useRef<HTMLInputElement>(null);
    const bankAccountRef = useRef<HTMLInputElement>(null);
    const faxNumberRef = useRef<HTMLInputElement>(null);
    const taxNumberRef = useRef<HTMLInputElement>(null);

    const [address, setAddress] = useState<Address | null>(null);
    const [fileSrc, setFileSrc] = useState<string | undefined>(data?.avatar);

    const handleFileChange = (fileSrc: string) => {
        setFileSrc(fileSrc);
    };

    const [resultPopup, setResultPopup] = useState<ResultPopup | null>(null);
    const closeResultPopup = () => setResultPopup(null);

    const [infoPopup, setInfoPopup] = useState<InfoPopup | null>(null);
    const closeInfoPopup = () => setInfoPopup(null);
    const openUpdateConfirm = () =>
        setInfoPopup(updatePartnerProfileConfirmPopupData);

    const updateProfileMutation = useMutation({
        mutationFn: async (payload: any) => {
            const response = await updatePartnerProfile({
                token: accessToken,
                partnerId: id!,
                profile: payload
            });
            return response;
        }
    });

    const handleUpdateProfile = async () => {
        closeInfoPopup();
        const response = await updateProfileMutation.mutateAsync({
            name: nameRef?.current?.value ?? data?.name,
            email: emailRef?.current?.value ?? data?.email,
            avatar: fileSrc !== data?.avatar ? fileSrc : undefined,
            phone_number: phoneRef?.current?.value ?? data?.phone,
            addresses: {
                address: address?.address,
                wardId: address?.ward?.id,
                districtId: address?.district?.id,
                provinceId: address?.province?.id
            },
            tax_number: taxNumberRef?.current?.value ?? data?.taxNumber,
            fax_number: faxNumberRef?.current?.value ?? data?.faxNumber,
            bank_acount: bankAccountRef?.current?.value ?? data?.bankAccount
        });

        if (response?.status === 200) {
            setResultPopup(UpdatePartnerProfileResult.Success);
            return;
        }
        if (response?.status >= 400) {
            setResultPopup(UpdatePartnerProfileResult.Error);
            return;
        }
    };

    if (!isFetching) console.log('data: ', data);

    return (
        <div className="w-full p-10">
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
                    closeAction={() => {
                        closeResultPopup();
                        if (resultPopup.popupType === ResultPopupType.Success)
                            goToPartnerDetailPage();
                    }}
                />
            )}
            <UserInfo />
            <div className="flex items-center gap-2">
                <IoChevronBack
                    className="text-blue-800 h-[30px] w-[30px] px-1 py-1 cursor-pointer hover:bg-blue-50 rounded-full"
                    onClick={goToPartnerDetailPage}
                />
                <img alt="form-icon" src={partnerIconSrc} />
                <h1 className="text-xl font-semibold">
                    Chỉnh sửa thông tin đối tác
                </h1>
            </div>
            <div className="max-w-[900px] flex flex-col justify-center space-y-5">
                <FileInput
                    fileSrc={fileSrc}
                    onChange={handleFileChange}
                    fallbackImage={
                        <RxAvatar className="w-full h-full min-[400px] text-gray-300" />
                    }
                />
                <div className=" max-w-[900px] flex gap-4">
                    <div className="space-y-5 flex-1">
                        <FormInput
                            name="Tên đối tác"
                            label="Tên đối tác"
                            icon={<FaUserCog />}
                            defaultValue={data?.name}
                            bg="bg-white"
                            ref={nameRef}
                        />
                        <FormInput
                            name="Số điện thoại"
                            label="Số điện thoại"
                            icon={<FaPhone />}
                            defaultValue={data?.phone}
                            bg="bg-white"
                            ref={phoneRef}
                        />
                        <FormInput
                            name="Email"
                            label="Email"
                            icon={<MdOutlineEmail />}
                            defaultValue={data?.email}
                            bg="bg-white"
                            ref={emailRef}
                        />
                    </div>
                    <div className="space-y-5 flex-1">
                        <ModalProvider>
                            <AddressPickerController
                                address={address}
                                updateAddress={setAddress}
                            />
                        </ModalProvider>
                        <FormInput
                            name="Tài khoản NH"
                            label="Tài khoản NH"
                            icon={<FaMoneyCheckAlt />}
                            defaultValue={data?.bankAccount}
                            bg="bg-white"
                            ref={bankAccountRef}
                        />
                        <FormInput
                            name="Số fax"
                            label="Số fax"
                            icon={<FaFax />}
                            defaultValue={data?.faxNumber}
                            bg="bg-white"
                            ref={faxNumberRef}
                        />
                        <FormInput
                            name="Mã số thuế"
                            label="Mã số thuế"
                            icon={<TbPigMoney />}
                            defaultValue={data?.taxNumber}
                            bg="bg-white"
                            ref={taxNumberRef}
                        />
                    </div>
                </div>
                <Button
                    className="mx-auto px-2 py-1 border rounded-md border-emerald-500 text-emerald-500 hover:bg-emerald-50 font-semibold w-fit"
                    text="Xác nhận"
                    action={openUpdateConfirm}
                />
            </div>
        </div>
    );
};

export default EditPartnerDetailPage;
