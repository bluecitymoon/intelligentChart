(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('CreditCardActivityTypeDialogController', CreditCardActivityTypeDialogController);

    CreditCardActivityTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CreditCardActivityType'];

    function CreditCardActivityTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CreditCardActivityType) {
        var vm = this;

        vm.creditCardActivityType = entity;
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
            if (vm.creditCardActivityType.id !== null) {
                CreditCardActivityType.update(vm.creditCardActivityType, onSaveSuccess, onSaveError);
            } else {
                CreditCardActivityType.save(vm.creditCardActivityType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:creditCardActivityTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
