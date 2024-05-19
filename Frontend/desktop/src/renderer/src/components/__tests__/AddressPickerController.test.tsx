import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import AddressPickerController from '../AddressPickerController';
import { useModal } from '@renderer/hooks';

// Mock the AddressPicker component
// disable eslint for mocking Address Picker
// eslint-disable-next-line react/display-name
jest.mock('../AddressPicker', () => () => (
    <div data-testid="address-picker">Address Picker</div>
));

// Mock the useModal hook
jest.mock('@renderer/hooks', () => ({
    useModal: jest.fn()
}));

describe('AddressPickerController', () => {
    const mockUseModal = useModal as jest.Mock;

    beforeEach(() => {
        mockUseModal.mockReturnValue({
            modalOpen: false,
            openModal: jest.fn()
        });
    });

    it('renders the address correctly', () => {
        const mockAddress = {
            address: '123 Street',
            ward: { name: 'Ward 1' },
            district: { name: 'District 1' },
            province: { name: 'Province 1' }
        };

        render(
            <AddressPickerController
                address={mockAddress}
                updateAddress={jest.fn()}
            />
        );

        expect(
            screen.getByText('123 Street, Ward 1, District 1, Province 1')
        ).toBeInTheDocument();
    });

    it('opens the modal when the location icon is clicked', () => {
        const openModal = jest.fn();
        mockUseModal.mockReturnValue({
            modalOpen: false,
            openModal
        });

        render(
            <AddressPickerController address={null} updateAddress={jest.fn()} />
        );

        const locationIcon = screen.getByTestId('location-icon');
        fireEvent.click(locationIcon);

        expect(openModal).toHaveBeenCalled();
    });

    it('renders AddressPicker when modal is open', () => {
        mockUseModal.mockReturnValue({
            modalOpen: true,
            openModal: jest.fn()
        });

        render(
            <AddressPickerController address={null} updateAddress={jest.fn()} />
        );

        expect(screen.getByTestId('address-picker')).toBeInTheDocument();
    });
});
