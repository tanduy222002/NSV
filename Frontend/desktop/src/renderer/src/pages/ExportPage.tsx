import { Button } from '@renderer/components';
import { useNavigate } from 'react-router-dom';

const ExportPage = () => {
    const navigate = useNavigate();
    const goToCreateExportTicket = () => navigate('/export/create');
    return (
        <div className="flex-1 h-screen px-8 py-6 relative">
            <Button
                text="Tạo phiếu mới"
                className="text-[#008767] border-[#008767] bg-[#16C098]"
                action={goToCreateExportTicket}
            />
        </div>
    );
};

export default ExportPage;
