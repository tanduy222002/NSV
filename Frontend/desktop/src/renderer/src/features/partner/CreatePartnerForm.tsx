import { useState, FormEvent } from 'react';
import {
    FormInput,
    AddressPickerController,
    ModalProvider,
    Button,
    InformationPopup,
    ConfirmationPopup
} from '@renderer/components';
import {
    createPartnerPopupData,
    createPartnerSuccessPopupData,
    defaultPartnerValue
} from '@renderer/constants/partner';
import { useLocalStorage } from '@renderer/hooks';
import { createPartner } from '@renderer/services/api/partner/createPartner';
import { InfoPopup, ResultPopup } from '@renderer/types/common';
import { Address, Partner } from '@renderer/types/partner';

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
    const handleCreatePartner = async (partner: Partner) => {
        const response = await createPartner({ token: accessToken, partner });
        if (response) setResultPopup(createPartnerSuccessPopupData);
        closeInfoPopup();
    };

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        openCreatePartnerPopup();
        console.log('partner: ', partner);
    };

    return (
        <>
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
                    className="justify-self-center px-2 py-1 border rounded-md border-sky-800 text-sky-800 hover:bg-sky-100 font-semibold"
                    text="Xác nhận"
                    type="submit"
                />
            </form>
        </>
    );
};

export default CreatePartnerForm;
