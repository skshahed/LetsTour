package com.example.forever.tour.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.forever.tour.ActivityInternal.ActivityAddExpense;
import com.example.forever.tour.ActivityInternal.ActivityViewExpense;
import com.example.forever.tour.CRUDClass.Expense;
import com.example.forever.tour.DatabaseSource.DatabaseSource;
import com.example.forever.tour.R;

import java.util.ArrayList;

/**
 * Created by Ashif Rahman on 5/6/2017.
 */

public class ExpenseAdapter extends ArrayAdapter<Expense> {
    private Context context;
    private ArrayList<Expense> expense;
    private DatabaseSource databaseSource;

    public ExpenseAdapter(@NonNull Context context, ArrayList<Expense> expense) {
        super(context, R.layout.row_layout_expense, expense);
        this.context    =   context;
        this.expense = expense;
    }

    class ViewHolder{
        TextView expenseDateTimeTV;
        TextView expenseDetalisTV;
        TextView expenseAmountTV;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater =   (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        databaseSource = new DatabaseSource(getContext());

        final ViewHolder holder;
        if(convertView==null) {
            holder          =   new ViewHolder();

            convertView     = inflater.inflate(R.layout.row_layout_expense, parent, false);
            holder.expenseDateTimeTV    = (TextView) convertView.findViewById(R.id.expenseDateTime);
            holder.expenseDetalisTV     = (TextView) convertView.findViewById(R.id.expenseDetalis);
            holder.expenseAmountTV      = (TextView) convertView.findViewById(R.id.expenseAmount);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final  int expenseID  = expense.get(position).getExpId();
        final int eventID = Integer.parseInt(expense.get(position).getEventId());
        final String userName = (expense.get(position).getUsername());
        //eventID = 9;

        holder.expenseDateTimeTV.setText(expense.get(position).getDateTime());
        holder.expenseDetalisTV.setText(expense.get(position).getExpDetails());
        holder.expenseAmountTV.setText("Tk. "+expense.get(position).getExpAmt()+".00");
//        holder.eventID = expense.get(position).getEventId();

        //final View finalConvertView = convertView;
        /*holder.expenseAmountTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int     rowId     = expense.get(position).getExpId();
               *//* holder.medicalImageIV.buildDrawingCache();
                Bitmap image= holder.medicalImageIV.getDrawingCache();
*//*

                parent.getContext().startActivity(new Intent(parent.getContext(),ActivityInternal.class)
                        //.putExtra("MH_id",rowId)
                        );
            }
        });*/


        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final Dialog dialog = new Dialog(getContext(), R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
                dialog.setContentView(R.layout.update_delete_popup);

                Button deleteBtn = (Button) dialog.findViewById(R.id.delete);
                Button updateBtn = (Button) dialog.findViewById(R.id.update);
                Button cancelBtn = (Button) dialog.findViewById(R.id.back);

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                        alert.setTitle("Delete Moment");
                        alert.setMessage("Are you sure to delete this expense ?");
                        alert.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean status = databaseSource.deleteExpense(expenseID);
                                if(status){
                                    Toast.makeText(context, "Expense Deleted", Toast.LENGTH_SHORT).show();
                                    getContext().startActivity(new Intent(getContext(),ActivityViewExpense.class)
                                            .putExtra("evId",eventID)
                                            .putExtra("userName",userName)
                                            );

                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                }else{
                                    Toast.makeText(context, "Couldn't Delete", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        alert.setNegativeButton("Cancel",null);
                        alert.show();

                    }
                });

                updateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getContext().startActivity(new Intent(getContext(),ActivityAddExpense.class)
                                .putExtra("exId",expense.get(position).getExpId())
                                .putExtra("evId",eventID)
                                .putExtra("userName",userName)

                                .putExtra("expDetails",expense.get(position).getExpDetails())
                                .putExtra("expAmt",expense.get(position).getExpAmt()));

                        notifyDataSetChanged();
                        dialog.dismiss();


                    }
                });



                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });


                dialog.show();


                return false;
            }
        });

        return convertView;
    }

}
