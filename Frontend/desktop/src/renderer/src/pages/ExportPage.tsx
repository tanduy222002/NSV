import { ModalProvider } from '@renderer/components';
import { CreateImportOrderFormController } from '@renderer/features/export';

const ExportPage = () => {
    return (
        <div className="flex-1 h-screen px-8 py-6 relative">
            <ModalProvider>
                <CreateImportOrderFormController />
            </ModalProvider>
        </div>
    );
};

export default ExportPage;
