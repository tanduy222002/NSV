import { CreatExportOrderForm } from '@renderer/features/export';
import { IoIosArrowBack } from 'react-icons/io';
import { useNavigate } from 'react-router-dom';

const CreateExportTicketPage = () => {
    const navigate = useNavigate();
    const goToExportPage = () => navigate('/export');
    return (
        <div className="px-5 py-5">
            <div className="flex items-center gap-2 my-5">
                <IoIosArrowBack onClick={goToExportPage} />
                <h1 className="text-lg font-semibold">Phiếu xuất/Tạo phiếu</h1>
            </div>
            <CreatExportOrderForm />
        </div>
    );
};

export default CreateExportTicketPage;
