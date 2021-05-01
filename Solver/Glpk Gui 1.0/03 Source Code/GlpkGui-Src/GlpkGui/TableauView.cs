using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Windows.Forms;

using GlpkSharp;

namespace GlpkGui
{
    public partial class TableauView : UserControl
    {
        public TableauView()
        {
            InitializeComponent();
            InitializeModel();
        }

        private LPProblem _LPProblem;

        private bool _NeedsSave = false;

        //public bool _Update = false;

        public bool _Correct = false;

        private SolverView solverView;

        private bool needsUpdate;

        public SolverView SolverView
        {
            get { return solverView; }
            set { solverView = value; }
        }


        public bool NeedsSave
        {
            get { return _NeedsSave; }
            set { _NeedsSave = value; if (value == true) { solverView.resetTextBoxes(); needsUpdate = true; } }
        }


        public LPProblem LPProblem
        {
            get { if (needsUpdate)updateLPProblem(); return _LPProblem; }
            set { _LPProblem = value; if (_LPProblem != null) { updateTableau(); solverView.resetTextBoxes(); NeedsSave = false; needsUpdate = false; } }
        }

        public Boolean IsProper()
        {
            return tableau.CurrentRow.ErrorText.Equals("") && bounds.CurrentRow.ErrorText.Equals("");
        }


        private void updateLPProblem()
        {
            Console.WriteLine("Updating LPProblem");

            if(_LPProblem != null)
            {
                _LPProblem.Destroy();
                _LPProblem.Dispose();
                _LPProblem = null;
            }

            _LPProblem = new LPProblem();

            _LPProblem.AddCols(bounds.RowCount);
            _LPProblem.AddRows(tableau.RowCount);


            for (int col = 0; col < bounds.RowCount; col++)
            {

                double lb = double.Parse(bounds.Rows[col].Cells[0].Value.ToString());
                double ub = double.Parse(bounds.Rows[col].Cells[1].Value.ToString());

                BOUNDSTYPE type;

                if (ub == 0)
                {
                    type = BOUNDSTYPE.Lower;
                    ub = Double.MaxValue;

                }
                else
                {
                    type = BOUNDSTYPE.Double;

                }
                _LPProblem.SetColBounds(col + 1, type, lb, ub);
                _LPProblem.SetColName(col + 1, bounds.Rows[col].HeaderCell.Value.ToString());

                bool integer = bool.Parse(bounds.Rows[col].Cells[2].Value.ToString());

                if (integer)
                {

                    _LPProblem.SetColKind(col + 1, COLKIND.Integer);
                }
                else
                {
                    _LPProblem.SetColKind(col + 1, COLKIND.Continuous);

                }

            }

            for (int row = 0; row < tableau.RowCount; row++)
            {

                int[] ind = new int[tableau.ColumnCount - 1];
                ind[0] = 1;

                double[] values = new double[tableau.ColumnCount - 1];
                values[0] = 1;
                for (int i = 1; i < tableau.ColumnCount - 1; i++)
                {
                    ind[i] = i;
                    values[i] = double.Parse(tableau.Rows[row].Cells[i - 1].Value.ToString());
                    if (row == 0)
                    {

                        _LPProblem.SetObjCoef(i, values[i]);
                    }

                }

                _LPProblem.SetMatRow(row + 1, ind, values);
                _LPProblem.SetRowName(row + 1, tableau.Rows[row].HeaderCell.Value.ToString());

                if (row > 0)
                {
                    String val = tableau.Rows[row].Cells[tableau.ColumnCount - 2].Value.ToString();

                    double bound = double.Parse(tableau.Rows[row].Cells[tableau.ColumnCount - 1].Value.ToString());

                    switch (val)
                    {
                        case ">=":
                            _LPProblem.SetRowBounds(row + 1, BOUNDSTYPE.Lower, bound, 0);
                            break;
                        case "<=":
                            _LPProblem.SetRowBounds(row + 1, BOUNDSTYPE.Upper, 0, bound);
                            break;
                        default:
                            _LPProblem.SetRowBounds(row + 1, BOUNDSTYPE.Fixed, 0, bound);
                            break;

                    }

                }
                else
                {
                    String val = tableau.Rows[row].Cells[tableau.ColumnCount-1].Value.ToString();

                    if (val.Equals("max"))
                    {
                        _LPProblem.ObjectiveDirection = OptimisationDirection.MAXIMISE;
                        _LPProblem.Name = "MAXIMIZE";
                    }
                    else
                    {
                        _LPProblem.ObjectiveDirection = OptimisationDirection.MINIMISE;
                        _LPProblem.Name = "MINIMIZE";
                    }
                }

            }

            needsUpdate = false;
        }

        private void updateTableau()
        {
            //_Update = false;



            int rows = _LPProblem.GetRowsCount();
            int cols = _LPProblem.GetColsCount();

            SetVariableCount(0);
            SetRestrictionCount(0);
            SetVariableCount(cols);
            SetRestrictionCount(rows - 1);

            int[] ind;
            double[] val;

            for (int i = 0; i < rows; i++)
            {
                _LPProblem.GetMatRow(i + 1, out ind, out val);
                for (int j = 1; j < ind.Length; j++)
                {

                    tableau.Rows[i].Cells[ind[j]-1].Value = val[j];

                }

                GlpkSharp.BOUNDSTYPE bounds = _LPProblem.GetRowBoundType(i + 1);

                if (i > 0)
                {
                    switch (bounds)
                    {
                        case BOUNDSTYPE.Lower:
                            tableau.Rows[i].Cells[cols].Value = ">=";
                            tableau.Rows[i].Cells[cols + 1].Value = _LPProblem.GetRowLb(i + 1);
                            break;
                        case BOUNDSTYPE.Upper:
                            tableau.Rows[i].Cells[cols].Value = "<=";
                            tableau.Rows[i].Cells[cols + 1].Value = _LPProblem.GetRowUb(i + 1);
                            break;
                        default:
                            tableau.Rows[i].Cells[cols].Value = "=";
                            tableau.Rows[i].Cells[cols + 1].Value = _LPProblem.GetRowUb(i + 1);
                            break;
                    }
                }
            }

            if (_LPProblem.ObjectiveDirection == OptimisationDirection.MAXIMISE)
            {
                tableau.Rows[0].Cells[cols + 1].Value = "max";
            }
            else
            {
                tableau.Rows[0].Cells[cols + 1].Value = "min";
            }

            for (int i = 0; i < cols; i++)
            {
                //dataGridView2.Rows.Add();
                //dataGridView2.Rows[i].HeaderCell.Value = "x" + (i + 1);
                COLKIND kind = _LPProblem.GetColKind(i + 1);
                if (!(kind == COLKIND.Continuous))
                {
                    bounds.Rows[i].Cells[2].Value = true;
                }
                else
                {
                    bounds.Rows[i].Cells[2].Value = false;
                }
                bounds.Rows[i].Cells[0].Value = _LPProblem.GetColLb(i + 1);

                double ub = _LPProblem.GetColUb(i + 1);
                bounds.Rows[i].Cells[1].Value = (ub == Double.MaxValue) ? 0 : ub;
            }
            // _Update = true;
        }

        private void InitializeModel()
        {
            //dataGridView1.CellValidating += new System.Windows.Forms.DataGridViewCellValidatingEventHandler(this.dataGridView1_CellValidating);


            tableau.RowHeadersWidth = 75;

            DataGridViewComboBoxColumn operatorColumn = new DataGridViewComboBoxColumn();
            operatorColumn.Items.AddRange("<=", "=", ">=");
            operatorColumn.Width = 50;
            operatorColumn.FillWeight = 50F;
            operatorColumn.SortMode = DataGridViewColumnSortMode.NotSortable;
            tableau.Columns.Add(operatorColumn);

            DataGridViewTextBoxColumn BVectorColumn = new DataGridViewTextBoxColumn();
            BVectorColumn.Width = 50;
            BVectorColumn.FillWeight = 50F;
            BVectorColumn.SortMode = DataGridViewColumnSortMode.NotSortable;
            BVectorColumn.HeaderText = "b";
            tableau.Columns.Add(BVectorColumn);

            tableau.Rows.Add(1);
            tableau.Rows[0].HeaderCell.Value = "Z";
            tableau.Rows[0].DefaultCellStyle.BackColor = Color.LemonChiffon;

            DataGridViewTextBoxCell TextBoxCell = new DataGridViewTextBoxCell();
            tableau.Rows[0].Cells[0] = TextBoxCell;
            tableau.Rows[0].Cells[0].ReadOnly = true;
            tableau.Rows[0].Cells[0].Value = "-->";

            DataGridViewComboBoxCell MinMaxCell = new DataGridViewComboBoxCell();
            MinMaxCell.Items.AddRange("min", "max");
            tableau.Rows[0].Cells[1] = MinMaxCell;

            //SetVariableCount(2);
            //SetRestrictionCount(5);

        }

        public void SetVariableCount(int count)
        {
            int current = tableau.Columns.Count - 2;

            if (count > current)
            {
                /*if (_Update)
                {
                    NeedsSave = true;
                    _LPProblem.AddCols(count - current);
                }*/
                for (int i = 0; i < count - current; i++)
                {

                    DataGridViewTextBoxColumn column = new DataGridViewTextBoxColumn();
                    column.SortMode = DataGridViewColumnSortMode.NotSortable;
                    column.Width = 50;
                    String Header = "X" + (current + i + 1);
                    column.HeaderText = Header;
                    tableau.Columns.Insert(current + i, column);
                    bounds.Rows.Add();
                    bounds.Rows[current + i].HeaderCell.Value = Header;

                    //if (_Update)
                    //{

                    bounds.Rows[current + i].Cells[0].Value = 0;
                    bounds.Rows[current + i].Cells[1].Value = 0;
                    bounds.Rows[current + i].Cells[2].Value = false;
                    //_LPProblem.SetColBounds(current + i + 1,BOUNDSTYPE.Lower,0,Double.MaxValue);

                    for (int j = 0; j < tableau.Rows.Count; j++)
                    {
                        tableau.Rows[j].Cells[current + i].Value = 0;
                    }
                    //}
                }

                NeedsSave = true;
            }
            else if (count < current)
            {
                for (int i = 0; i < current - count; i++)
                {
                    tableau.Columns.RemoveAt(count);

                    bounds.Rows.RemoveAt(count);

                    /*if (_Update)
                    {
                        NeedsSave = true;
                        _LPProblem.DeleteCols(new int[] { 1, count + i });
                    }*/
                }
                NeedsSave = true;
            }
        }

        public void SetRestrictionCount(int count)
        {
            int current = tableau.Rows.Count - 1;

            if (count > current)
            {
                /*if (_Update)
                {
                    NeedsSave = true;
                    _LPProblem.AddRows(count - current);
                }*/

                for (int i = 0; i < count - current; i++)
                {
                    DataGridViewRow row = new DataGridViewRow();
                    String Header = "R" + (current + i + 1);
                    tableau.Rows.Insert(current + i + 1, row);
                    tableau.Rows[current + i + 1].HeaderCell.Value = Header;

                    //if (_Update)
                    //{
                    for (int j = 0; j < tableau.Columns.Count; j++)
                    {
                        if (j == tableau.Columns.Count - 2)
                        {
                            tableau.Rows[current + i + 1].Cells[j].Value = "=";
                            //_LPProblem.SetRowBounds(current + i + 2, BOUNDSTYPE.Fixed, 0, 0);
                        }
                        else
                        {
                            tableau.Rows[current + i + 1].Cells[j].Value = 0;
                        }
                    }
                    //}
                }
                NeedsSave = true;
            }
            else if (count < current)
            {
                for (int i = 0; i < current - count; i++)
                {
                    tableau.Rows.RemoveAt(current - i);
                    /*if (_Update)
                    {
                        NeedsSave = true;
                        _LPProblem.DeleteRows(new int[] { 1, current - i + 1});
                    }*/
                }
                NeedsSave = true;
            }
        }



        private void dataGridView1_CellValidating(object sender, DataGridViewCellValidatingEventArgs e)
        {
            if (e.ColumnIndex < tableau.ColumnCount - 2 || (e.ColumnIndex == tableau.ColumnCount - 1 && e.RowIndex > 0))
            {
                tableau.Rows[e.RowIndex].ErrorText = "";
                double newDouble;


                // Don't try to validate the 'new row' until finished 
                // editing since there
                // is not any point in validating its initial value.
                if (tableau.Rows[e.RowIndex].IsNewRow) { return; }
                if (!double.TryParse(e.FormattedValue.ToString(),
                    out newDouble))
                {
                    if (_Correct)
                    {
                        tableau.EndEdit();
                        tableau.CurrentCell.Value = 0;
                        _Correct = false;
                        return;
                    }
                    e.Cancel = true;
                    //System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(TableauView));
                    tableau.Rows[e.RowIndex].ErrorText = global::GlpkGui.Properties.Resources.errorDouble;
                    return;
                }
            }
            object current = tableau.Rows[e.RowIndex].Cells[e.ColumnIndex].Value;
            object neo = e.FormattedValue;
            if (current != null && neo != null)
            {
                if (current.ToString().Equals(neo.ToString()))
                {
                    return;
                }
                else
                {
                    NeedsSave = true;
                }
            }

        }

        private void dataGridView2_CellValidating(object sender, DataGridViewCellValidatingEventArgs e)
        {


            if (e.ColumnIndex < bounds.ColumnCount - 1)
            {
                bounds.Rows[e.RowIndex].ErrorText = "";
                double newDouble;

                // Don't try to validate the 'new row' until finished 
                // editing since there
                // is not any point in validating its initial value.
                if (bounds.Rows[e.RowIndex].IsNewRow) { return; }
                if (!double.TryParse(e.FormattedValue.ToString(),
                    out newDouble))
                {
                    if (_Correct)
                    {
                        bounds.EndEdit();
                        bounds.CurrentCell.Value = 0;
                        _Correct = false;
                        return;
                    }
                    e.Cancel = true;
                    //System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(TableauView));
                    bounds.Rows[e.RowIndex].ErrorText = global::GlpkGui.Properties.Resources.errorDouble;
                    return;
                }

            }

            object current = bounds.Rows[e.RowIndex].Cells[e.ColumnIndex].Value;
            object neo = e.FormattedValue;
            if (current != null && neo != null)
            {
                if (current.ToString().Equals(neo.ToString()))
                {
                    return;
                }
                else
                {
                    NeedsSave = true;
                }
            }

        }

        public void EmptyProblem()
        {
            SetRestrictionCount(0);
            SetVariableCount(0);
            SetRestrictionCount(2);
            SetVariableCount(2);
            tableau.Rows[0].Cells[tableau.ColumnCount - 1].Value = "min";
            updateLPProblem();
            NeedsSave = false;
        }


    }
}
