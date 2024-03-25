import { Button } from '@renderer/components';
import { useModal } from '@renderer/hooks';
import CreateWarehouseForm from './CreateWarehouseForm';
const CreateWarehouseFormController = () => {
    const { modalOpen, openModal } = useModal();

    return (
        <>
            <Button
                text="Tạo kho mới"
                className="text-[#008767] border-[#008767] bg-[#16C098]"
                action={openModal}
            />
            {modalOpen && <CreateWarehouseForm />}
        </>
    );
};

export default CreateWarehouseFormController;
