import { IoChevronBack } from 'react-icons/io5';
import { UserInfo } from '@renderer/components';
import { useNavigate } from 'react-router-dom';
import { CreateImportTicketForm } from '@renderer/features/import';
import warehouseIconSrc from '@renderer/assets/warehouse-icon.png';

const CreateImportTicketPage = () => {
    const navigate = useNavigate();
    const goToImportTicketPage = () => navigate('/import');
    return (
        <div className="w-full px-5 py-5">
            <UserInfo />
            <div className="flex items-center gap-2">
                <IoChevronBack
                    className="text-blue-800 h-[30px] w-[30px] px-1 py-1 cursor-pointer hover:bg-blue-50 rounded-full"
                    onClick={goToImportTicketPage}
                />
                <img alt="form-icon" src={warehouseIconSrc} />
                <h1 className="text-xl font-semibold">Tạo phiếu nhập</h1>
            </div>
            <CreateImportTicketForm />
        </div>
    );
};

export default CreateImportTicketPage;
