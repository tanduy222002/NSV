import { Button } from '@renderer/components';
import { useModal } from '@renderer/hooks';
import CreateRackingLayoutForm from './CreateRackingLayoutForm';
const CreateRackingLayoutFormController = () => {
    const { modalOpen, openModal } = useModal();

    return (
        <>
            <Button
                text="Tạo sơ đồ kho"
                className="text-[#008767] border-[#008767] bg-[#16C098]"
                action={openModal}
            />
            {modalOpen && <CreateRackingLayoutForm />}
        </>
    );
};

export default CreateRackingLayoutFormController;
