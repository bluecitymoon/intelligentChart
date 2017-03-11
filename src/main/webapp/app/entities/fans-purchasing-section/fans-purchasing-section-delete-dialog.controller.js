(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('FansPurchasingSectionDeleteController',FansPurchasingSectionDeleteController);

    FansPurchasingSectionDeleteController.$inject = ['$uibModalInstance', 'entity', 'FansPurchasingSection'];

    function FansPurchasingSectionDeleteController($uibModalInstance, entity, FansPurchasingSection) {
        var vm = this;

        vm.fansPurchasingSection = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FansPurchasingSection.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
