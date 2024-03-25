import { useModal } from '@renderer/hooks';
import { Button } from '@renderer/components';
import CreateImportOrderForm from './CreateExportOrderForm';

const CreateImportOrderFormController = () => {
    const { modalOpen, openModal } = useModal();
    return (
        <>
            <Button
                text="Tạo phiếu mới"
                className="text-[#008767] border-[#008767] bg-[#16C098]"
                action={openModal}
            />
            {modalOpen && <CreateImportOrderForm />}
        </>
    );
};

export default CreateImportOrderFormController;
