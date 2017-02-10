(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonPrizeDeleteController',PersonPrizeDeleteController);

    PersonPrizeDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonPrize'];

    function PersonPrizeDeleteController($uibModalInstance, entity, PersonPrize) {
        var vm = this;

        vm.personPrize = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonPrize.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
