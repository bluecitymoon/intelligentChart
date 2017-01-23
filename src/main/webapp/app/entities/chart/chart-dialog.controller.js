(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('ChartDialogController', ChartDialogController);

    ChartDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Chart'];

    function ChartDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Chart) {
        var vm = this;

        vm.chart = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.chart.id !== null) {
                Chart.update(vm.chart, onSaveSuccess, onSaveError);
            } else {
                Chart.save(vm.chart, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:chartUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
