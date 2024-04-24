import AddressPicker from './AddressPicker';
import { FaLocationDot, FaMapLocationDot } from 'react-icons/fa6';
import { useModal } from '@renderer/hooks';

type AddressPickerControllerProps = {
    updateAddress: (address: any) => void;
};

const AddressPickerController = ({
    updateAddress
}: AddressPickerControllerProps) => {
    const { modalOpen, openModal } = useModal();
    return (
        <div className="relative border border-sky-700 rounded-md w-full px-2 py-2">
            <div className="bg-white flex items-center gap-1 absolute px-2 -top-4 left-5 text-sky-700">
                <FaMapLocationDot />
                <div>Địa chỉ</div>
            </div>
            <FaLocationDot
                className="text-sky-700 hover:text-red-500 cursor-pointer ml-auto"
                onClick={openModal}
            />
            {modalOpen && <AddressPicker updateAddressDetail={updateAddress} />}
        </div>
    );
};

export default AddressPickerController;
