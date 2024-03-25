import { ModalContext } from '@renderer/components/ModalProvider';
import { useContext } from 'react';

export function useModal() {
    return useContext(ModalContext);
}
