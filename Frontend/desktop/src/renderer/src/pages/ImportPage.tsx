// import { ModalProvider } from '@renderer/components';
import warehouseIconSrc from '@renderer/assets/warehouse-icon.png';
import { UserInfo, Button } from '@renderer/components';
import { useNavigate } from 'react-router-dom';

const ImportPage = () => {
    const navigate = useNavigate();
    const goToCreateImportTicketPage = () => navigate('/import/create');
    return (
        <div className="w-full px-5 py-5">
            <UserInfo />
            <div className="flex items-center gap-2 mb-5">
                <img src={warehouseIconSrc} />
                <h1 className="font-semibold text-xl">Phiếu nhập</h1>
            </div>
            <Button
                className="mb-5 px-2 py-1 border border-emerald-600 rounded-md text-emerald-600 text-base font-semibold w-fit"
                text="Tạo phiếu"
                action={goToCreateImportTicketPage}
            />
        </div>
    );
};

export default ImportPage;
