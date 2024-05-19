import AddressPicker from './AddressPicker';
import { FaLocationDot, FaMapLocationDot } from 'react-icons/fa6';
import { useModal } from '@renderer/hooks';

type AddressPickerControllerProps = {
    updateAddress: (address: any) => void;
    address: any | null;
};

const AddressPickerController = ({
    address,
    updateAddress
}: AddressPickerControllerProps) => {
    const { modalOpen, openModal } = useModal();
    return (
        <div className="relative border border-sky-700 rounded-md w-full px-5 py-2">
            <div className="bg-white flex items-center gap-1 absolute px-2 -top-4 left-5 text-sky-700">
                <FaMapLocationDot />
                <div>Địa chỉ</div>
            </div>
            <div className="flex items-center">
                <div className="text-sm font-semibold">
                    {address &&
                        `${address?.address}, ${address?.ward?.name}, ${address?.district?.name}, ${address?.province?.name}`}
                </div>
                <FaLocationDot
                    data-testid="location-icon"
                    onClick={openModal}
                    className="ml-auto hover:text-red-500"
                />
            </div>
            {modalOpen && <AddressPicker updateAddressDetail={updateAddress} />}
        </div>
    );
};

export default AddressPickerController;
