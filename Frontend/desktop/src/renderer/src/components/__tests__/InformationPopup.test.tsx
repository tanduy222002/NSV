import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import InformationPopup from '../InfomationPopup';
import { ResultPopupType } from '@renderer/types/common';

describe('InformationPopup Component', () => {
    const mockCloseAction = jest.fn();

    const renderComponent = (popupType) => {
        return render(
            <InformationPopup
                title="Test Title"
                body="This is a test body"
                popupType={popupType}
                closeAction={mockCloseAction}
            />
        );
    };

    afterEach(() => {
        mockCloseAction.mockClear();
    });

    test('renders correctly for Error popup', () => {
        renderComponent(ResultPopupType.Error);

        expect(screen.getByText('Test Title')).toBeInTheDocument();
        expect(screen.getByText('This is a test body')).toBeInTheDocument();
        expect(screen.getByText('Test Title')).toHaveClass('text-red-500');
        expect(screen.getByRole('button', { name: /đóng/i })).toHaveClass(
            'bg-red-500'
        );
    });

    test('renders correctly for Success popup', () => {
        renderComponent(ResultPopupType.Success);

        expect(screen.getByText('Test Title')).toBeInTheDocument();
        expect(screen.getByText('This is a test body')).toBeInTheDocument();
        expect(screen.getByText('Test Title')).toHaveClass('text-emerald-500');
        expect(screen.getByRole('button', { name: /đóng/i })).toHaveClass(
            'bg-emerald-500'
        );
    });

    test('renders correctly for Info popup', () => {
        renderComponent(ResultPopupType.Info);

        expect(screen.getByText('Test Title')).toBeInTheDocument();
        expect(screen.getByText('This is a test body')).toBeInTheDocument();
        expect(screen.getByText('Test Title')).toHaveClass('text-sky-800');
        expect(screen.getByRole('button', { name: /đóng/i })).toHaveClass(
            'bg-red-500'
        );
    });

    test('calls closeAction on button click', () => {
        renderComponent(ResultPopupType.Error);

        const closeButton = screen.getByRole('button', { name: /đóng/i });
        fireEvent.click(closeButton);

        expect(mockCloseAction).toHaveBeenCalledTimes(1);
    });
});
