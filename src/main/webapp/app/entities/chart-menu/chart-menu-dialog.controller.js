(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('ChartMenuDialogController', ChartMenuDialogController);

    ChartMenuDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ChartMenu', 'Chart', 'Menu'];

    function ChartMenuDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ChartMenu, Chart, Menu) {
        var vm = this;

        vm.chartMenu = entity;
        vm.clear = clear;
        vm.save = save;
        vm.charts = Chart.query();
        vm.menus = Menu.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.chartMenu.id !== null) {
                ChartMenu.update(vm.chartMenu, onSaveSuccess, onSaveError);
            } else {
                ChartMenu.save(vm.chartMenu, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:chartMenuUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
