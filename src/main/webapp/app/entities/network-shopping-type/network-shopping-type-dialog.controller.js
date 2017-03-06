(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('NetworkShoppingTypeDialogController', NetworkShoppingTypeDialogController);

    NetworkShoppingTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'NetworkShoppingType'];

    function NetworkShoppingTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, NetworkShoppingType) {
        var vm = this;

        vm.networkShoppingType = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.networkShoppingType.id !== null) {
                NetworkShoppingType.update(vm.networkShoppingType, onSaveSuccess, onSaveError);
            } else {
                NetworkShoppingType.save(vm.networkShoppingType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:networkShoppingTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
