object Einstellung: TEinstellung
  Left = 223
  Top = 170
  Width = 328
  Height = 267
  BorderIcons = [biSystemMenu]
  Caption = 'Einstellungen'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  OnShow = FormShow
  PixelsPerInch = 96
  TextHeight = 13
  object MaxStandLabel: TLabel
    Left = 24
    Top = 24
    Width = 155
    Height = 13
    Caption = 'Maximale Anzahl von Standorten'
  end
  object MaxStandorte: TEdit
    Left = 24
    Top = 40
    Width = 57
    Height = 21
    Hint = 'Geben Sie einen Wert zwischen 4 und 50 ein'
    ParentShowHint = False
    ShowHint = True
    TabOrder = 0
    Text = '10'
    OnChange = MaxStandorteChange
    OnKeyPress = MaxStandorteKeyPress
  end
  object OKButton: TButton
    Left = 50
    Top = 200
    Width = 100
    Height = 25
    Caption = 'OK'
    Default = True
    ModalResult = 1
    TabOrder = 1
    OnClick = OKButtonClick
  end
  object DetailBox: TCheckBox
    Left = 24
    Top = 104
    Width = 289
    Height = 25
    Caption = 'Detaillierte Ausgabe des Entscheidungsbaumverfahrens'
    TabOrder = 2
  end
  object ErgebnisBox: TCheckBox
    Left = 24
    Top = 136
    Width = 289
    Height = 25
    Caption = 'Bei Abbruch das derzeit optimale Ergebnis ausgeben'
    TabOrder = 3
  end
  object AbbrechenButton: TButton
    Left = 170
    Top = 200
    Width = 100
    Height = 25
    Cancel = True
    Caption = 'Abbrechen'
    ModalResult = 3
    TabOrder = 4
    OnClick = AbbrechenButtonClick
  end
end
