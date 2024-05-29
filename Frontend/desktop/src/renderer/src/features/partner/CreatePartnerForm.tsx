import { useState, FormEvent } from 'react';
import {
    FormInput,
    AddressPickerController,
    ModalProvider,
    Button,
    InformationPopup,
    ConfirmationPopup,
    Loading
} from '@renderer/components';
import {
    CreatePartnerResult,
    createPartnerPopupData,
    defaultPartnerValue
} from '@renderer/constants/partner';
import { useLocalStorage } from '@renderer/hooks';
import { createPartner } from '@renderer/services/api/partner/createPartner';
import { Address, InfoPopup, ResultPopup } from '@renderer/types/common';
import { Partner } from '@renderer/types/partner';
import { useMutation } from '@tanstack/react-query';

const CreatePartnerForm = () => {
    const [partner, setPartner] = useState(defaultPartnerValue);

    const updatePartnerAddress = (address: Address) =>
        setPartner((prev) => ({ ...prev, address }));

    const updatePartner = (key: string) => {
        return (value) => setPartner((prev) => ({ ...prev, [key]: value }));
    };

    const [infoPopup, setInfoPopup] = useState<InfoPopup | null>(null);
    const openCreatePartnerPopup = () => setInfoPopup(createPartnerPopupData);
    const closeInfoPopup = () => setInfoPopup(null);

    const [resultPopup, setResultPopup] = useState<ResultPopup | null>(null);
    const closeResultPopup = () => setResultPopup(null);

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const createPartnerMutation = useMutation({
        mutationFn: async (payload: any) => {
            const response = await createPartner(payload);
            return response;
        }
    });

    const handleCreatePartner = async (partner: Partner) => {
        closeInfoPopup();
        const response = await createPartnerMutation.mutateAsync({
            token: accessToken,
            partner: partner
        });
        if (response?.status === 200) {
            setResultPopup(CreatePartnerResult.Success);
        }
        if (response?.status >= 400) {
            setResultPopup(CreatePartnerResult.Error);
        }
    };

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        openCreatePartnerPopup();
        console.log('partner: ', partner);
    };

    return (
        <>
            {createPartnerMutation.isPending && <Loading />}
            {infoPopup && (
                <ConfirmationPopup
                    title={infoPopup.title}
                    body={infoPopup.body}
                    confirmAction={() => handleCreatePartner(partner)}
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
            <form
                onSubmit={handleSubmit}
                className="w-full max-w-[600px] space-y-5 flex flex-col"
            >
                <FormInput
                    label="Đối tác"
                    name="Đối tác"
                    onChange={updatePartner('name')}
                    bg="bg-white"
                />
                <FormInput
                    label="Email"
                    name="Email"
                    onChange={updatePartner('email')}
                    bg="bg-white"
                />
                <ModalProvider>
                    <AddressPickerController
                        address={partner?.address}
                        updateAddress={updatePartnerAddress}
                    />
                </ModalProvider>
                <FormInput
                    label="Số điện thoại"
                    name="Số điện thoại"
                    onChange={updatePartner('phoneNumber')}
                    bg="bg-white"
                />
                <FormInput
                    label="STK ngân hàng"
                    name="STK ngân hàng"
                    onChange={updatePartner('bankAccount')}
                    bg="bg-white"
                />
                <FormInput
                    label="Số fax"
                    name="Số fax"
                    onChange={updatePartner('faxNumber')}
                    bg="bg-white"
                />
                <FormInput
                    label="Mã số thuế"
                    name="Mã số thuế"
                    onChange={updatePartner('taxNumber')}
                    bg="bg-white"
                />
                <Button
                    className="justify-self-center px-2 py-1 border rounded-md border-emerald-500 text-emerald-500 hover:bg-emerald-50 font-semibold"
                    text="Xác nhận"
                    type="submit"
                />
            </form>
        </>
    );
};

export default CreatePartnerForm;
