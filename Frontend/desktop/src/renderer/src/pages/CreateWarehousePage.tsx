import { IoChevronBack } from 'react-icons/io5';
import { useNavigate } from 'react-router-dom';
import { CreateWarehouseForm } from '@renderer/features/warehouse';
import { UserInfo } from '@renderer/components';
import warehouseIconSrc from '@renderer/assets/warehouse-icon.png';

const CreateWarehousePage = () => {
    const navigate = useNavigate();
    const goToWarehousePage = () => navigate('/warehouse');
    return (
        <div className="w-full px-5 py-5">
            <UserInfo />
            <div className="flex items-center gap-2">
                <IoChevronBack
                    className="text-blue-800 h-[30px] w-[30px] px-1 py-1 cursor-pointer hover:bg-blue-50 rounded-full"
                    onClick={goToWarehousePage}
                />
                <img alt="form-icon" src={warehouseIconSrc} />
                <h1 className="text-xl font-semibold">Tạo sơ đồ kho</h1>
            </div>
            <CreateWarehouseForm />
        </div>
    );
};

export default CreateWarehousePage;
