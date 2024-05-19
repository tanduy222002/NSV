import { useNavigate } from 'react-router-dom';
import { IoChevronBack } from 'react-icons/io5';
import { UserInfo } from '@renderer/components';
import warehouseIconSrc from '@renderer/assets/warehouse-icon.png';
import { CreateExportTicketForm } from '@renderer/features/export';

const CreateExportTicketPage = () => {
    const navigate = useNavigate();
    const goToExportTicketPage = () => navigate('/export');
    return (
        <div className="w-full px-5 py-5">
            <UserInfo />
            <div className="flex items-center gap-2 mb-5">
                <IoChevronBack
                    className="text-blue-800 h-[30px] w-[30px] px-1 py-1 cursor-pointer hover:bg-blue-50 rounded-full"
                    onClick={goToExportTicketPage}
                />
                <img alt="form-icon" src={warehouseIconSrc} />
                <h1 className="text-xl font-semibold">Tạo phiếu xuất</h1>
            </div>
            <CreateExportTicketForm />
        </div>
    );
};

export default CreateExportTicketPage;
