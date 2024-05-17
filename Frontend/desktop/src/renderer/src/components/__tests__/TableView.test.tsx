import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import TableView, { ColumnType } from '../TableView';
import { vi } from 'vitest';

describe('TableView Component Unit Test', () => {
    const columns = [
        { id: 0, title: 'Name', sortable: true, type: ColumnType.Text },
        { id: 1, title: 'Profile', sortable: false, type: ColumnType.Image },
        { id: 2, title: 'Actions', sortable: false, type: ColumnType.Action }
    ];

    const items = [
        {
            id: '1',
            name: 'John Doe',
            profile: 'profile1.png'
        },
        { id: '2', name: 'Jane Doe', profile: 'profile2.png' }
    ];

    const viewAction = vi.fn();
    const editAction = vi.fn();
    const deleteAction = vi.fn();

    it('renders correctly with data', () => {
        render(
            <TableView
                columns={columns}
                items={items}
                viewAction={viewAction}
                editAction={editAction}
                deleteAction={deleteAction}
            />
        );

        expect(screen.getByText('Name')).toBeInTheDocument();
        expect(screen.getByText('Profile')).toBeInTheDocument();
        expect(screen.getByText('Actions')).toBeInTheDocument();
    });

    it('contains table menu', () => {
        render(
            <TableView
                columns={columns}
                items={items}
                viewAction={viewAction}
                editAction={editAction}
                deleteAction={deleteAction}
            />
        );

        const tableMenu = screen.getByTestId('table-menu#2');
        expect(tableMenu).toBeInTheDocument();
    });

    it('displays empty state when no items', () => {
        render(<TableView columns={columns} items={[]} />);

        expect(screen.getByText('Chưa có dữ liệu')).toBeInTheDocument();
    });
});
