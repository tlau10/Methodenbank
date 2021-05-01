object Anzeige: TAnzeige
  Left = 551
  Height = 143
  Top = 324
  Width = 312
  BorderIcons = [biSystemMenu]
  BorderStyle = bsDialog
  Caption = 'Berechnung'
  ClientHeight = 143
  ClientWidth = 312
  Color = clBtnFace
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  FormStyle = fsStayOnTop
  OnClose = FormClose
  LCLVersion = '1.4.0.4'
  object FortschrittLabel: TLabel
    Left = 32
    Height = 13
    Top = 80
    Width = 29
    Alignment = taRightJustify
    Caption = '100 %'
    ParentColor = False
  end
  object ArtLabel: TLabel
    Left = 32
    Height = 13
    Top = 13
    Width = 60
    Caption = 'Rechenart'
    Color = clBtnFace
    Font.Color = clWindowText
    Font.Height = -11
    Font.Name = 'MS Sans Serif'
    Font.Style = [fsBold]
    ParentColor = False
    ParentFont = False
    Transparent = False
  end
  object StatusLabel: TLabel
    Left = 30
    Height = 13
    Top = 37
    Width = 247
    Caption = '10000000 von 10000000000000000 Berechnungen'
    Font.Color = clCaptionText
    Font.Height = -11
    Font.Name = 'MS Sans Serif'
    ParentColor = False
    ParentFont = False
  end
  object AbbruchButton: TButton
    Left = 112
    Height = 25
    Top = 104
    Width = 100
    Cancel = True
    Caption = 'Abbrechen'
    OnClick = AbbruchButtonClick
    TabOrder = 0
  end
  object Fortschritt: TProgressBar
    Left = 30
    Height = 17
    Top = 60
    Width = 250
    TabOrder = 1
  end
end
