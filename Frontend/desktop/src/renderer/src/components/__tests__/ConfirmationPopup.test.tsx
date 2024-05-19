import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import ConfirmationPopup from '../ConfirmationPopup';

describe('ConfirmationPopup component', () => {
    const setup = (props = {}) => {
        const initialProps = {
            title: 'Test Title',
            body: 'Test Body',
            confirmAction: jest.fn(),
            cancelAction: jest.fn(),
            ...props
        };
        return render(<ConfirmationPopup {...initialProps} />);
    };

    test('renders without crashing', () => {
        setup();
        expect(screen.getByText('Test Title')).toBeInTheDocument();
        expect(screen.getByText('Test Body')).toBeInTheDocument();
    });

    test('displays the title and body', () => {
        setup();
        const title = screen.getByText('Test Title');
        const body = screen.getByText('Test Body');

        expect(title).toBeInTheDocument();
        expect(body).toBeInTheDocument();
    });

    test('calls confirmAction when confirm button is clicked', () => {
        const confirmAction = jest.fn();
        setup({ confirmAction });

        const confirmButton = screen.getByText('Xác nhận');
        fireEvent.click(confirmButton);

        expect(confirmAction).toHaveBeenCalled();
    });

    test('calls cancelAction when cancel button is clicked', () => {
        const cancelAction = jest.fn();
        setup({ cancelAction });

        const cancelButton = screen.getByText('Hủy bỏ');
        fireEvent.click(cancelButton);

        expect(cancelAction).toHaveBeenCalled();
    });
});
