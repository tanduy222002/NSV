import { render, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import RowAction from '../RowAction';

describe('RowAction Component', () => {
    const mockId = '1';
    const mockViewAction = jest.fn();
    const mockEditAction = jest.fn();
    const mockDeleteAction = jest.fn();

    afterEach(() => {
        jest.clearAllMocks();
    });

    test('renders without crashing', () => {
        render(<RowAction id={mockId} />);
    });

    test('displays toggle menu when clicked', () => {
        const { getByTestId } = render(
            <RowAction
                id={mockId}
                viewAction={mockViewAction}
                editAction={mockEditAction}
                deleteAction={mockDeleteAction}
            />
        );

        const button = getByTestId('menu-control');

        fireEvent.click(button);
        const viewBtn = getByTestId('view-btn');
        const editBtn = getByTestId('edit-btn');
        const deleteBtn = getByTestId('delete-btn');
        expect(viewBtn).toBeInTheDocument();
        expect(editBtn).toBeInTheDocument();
        expect(deleteBtn).toBeInTheDocument();

        fireEvent.click(button);
        expect(viewBtn).not.toBeInTheDocument();
        expect(editBtn).not.toBeInTheDocument();
        expect(deleteBtn).not.toBeInTheDocument();
    });

    test('calls viewAction, editAction, deleteAction with id when respective buttons are clicked', () => {
        const { getByTestId } = render(
            <RowAction
                id={mockId}
                viewAction={mockViewAction}
                editAction={mockEditAction}
                deleteAction={mockDeleteAction}
            />
        );
        // open the menu
        const button = getByTestId('menu-control');

        // test view button
        fireEvent.click(button);
        const viewButton = getByTestId('view-btn');
        fireEvent.click(viewButton);
        expect(mockViewAction).toHaveBeenCalledWith(mockId);

        // test edit button
        fireEvent.click(button);
        const editButton = getByTestId('edit-btn');
        fireEvent.click(editButton);
        expect(mockViewAction).toHaveBeenCalledWith(mockId);

        // test delete button
        fireEvent.click(button);
        const deleteButton = getByTestId('delete-btn');
        fireEvent.click(deleteButton);
        expect(mockDeleteAction).toHaveBeenCalledWith(mockId);
    });
});
