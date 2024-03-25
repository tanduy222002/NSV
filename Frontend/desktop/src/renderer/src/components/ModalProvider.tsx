import { useState, createContext, ReactNode } from 'react';

type ModalContextType = {
    modalOpen: boolean;
    closeModal?: () => void;
    openModal?: () => void;
};

type ModalProviderProps = { children: ReactNode };

export const ModalContext = createContext<ModalContextType>({
    modalOpen: false
});

const ModalProvider = ({ children }: ModalProviderProps) => {
    const [modalOpen, setModalOpen] = useState(false);

    const openModal = () => setModalOpen(true);
    const closeModal = () => setModalOpen(false);

    return (
        <ModalContext.Provider value={{ modalOpen, openModal, closeModal }}>
            {children}
        </ModalContext.Provider>
    );
};

export default ModalProvider;
